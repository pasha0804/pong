import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public final class EingeschraenkteLaenge extends PlainDocument {

    private final int limit;

    public EingeschraenkteLaenge(int limit) {
        this.limit = limit;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a)
            throws BadLocationException {
        if (str == null)
            return;

        if ((getLength() + str.length()) <= limit) {                    // limitiert Stringlänge
            super.insertString(offs, str, a);
        }
    }
}