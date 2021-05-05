package modzo.compare.command.core.tools

class Text {
    static String clean(String string) {
        if (string == null) {
            return null
        }
        String initial = string.replaceAll("[^a-zA-Z0-9']", ' ')
        return replaceRecursivelyAll(initial, '  ', ' ').trim()
    }

    static String replaceRecursivelyAll(String text, String what, String toWhat) {
        String finalText = text
        while (finalText.contains(what)) {
            finalText = finalText.replaceAll(what, toWhat)
        }
        return finalText
    }
}
