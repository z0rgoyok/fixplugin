package ru.fixprice.fixplugin

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import java.util.UUID

class GenerateUuidIntention : PsiElementBaseIntentionAction(), IntentionAction {

    override fun getFamilyName(): String = "UUID"

    override fun getText(): String = "Generate UUID"

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        if (editor == null) return false

        val stringInfo = findStringLiteralInfo(editor) ?: return false
        return stringInfo.content.isBlank()
    }

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        if (editor == null) return

        val stringInfo = findStringLiteralInfo(editor) ?: return
        val uuid = UUID.randomUUID().toString()

        val document = editor.document
        document.replaceString(stringInfo.contentStart, stringInfo.contentEnd, uuid)
    }

    private fun findStringLiteralInfo(editor: Editor): StringLiteralInfo? {
        val offset = editor.caretModel.offset
        val document = editor.document
        val text = document.text

        // Ищем открывающую кавычку слева
        var openQuote = -1
        var i = offset - 1
        while (i >= 0) {
            val char = text[i]
            if (char == '"' && (i == 0 || text[i - 1] != '\\')) {
                openQuote = i
                break
            }
            if (char == '\n') break
            i--
        }

        if (openQuote == -1) return null

        // Ищем закрывающую кавычку справа
        var closeQuote = -1
        i = offset
        while (i < text.length) {
            val char = text[i]
            if (char == '"' && text[i - 1] != '\\') {
                closeQuote = i
                break
            }
            if (char == '\n') break
            i++
        }

        if (closeQuote == -1) return null

        val contentStart = openQuote + 1
        val contentEnd = closeQuote
        val content = text.substring(contentStart, contentEnd)

        return StringLiteralInfo(
            content = content,
            contentStart = contentStart,
            contentEnd = contentEnd
        )
    }

    private data class StringLiteralInfo(
        val content: String,
        val contentStart: Int,
        val contentEnd: Int
    )
}
