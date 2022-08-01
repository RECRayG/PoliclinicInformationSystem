package registryApplication.searhModel;

import javax.swing.*;

public class myRowFilter extends RowFilter
{
    private String text;
    private int columnIndex;

    public myRowFilter(String text, int columnIndex)
    {
        this.text = text;
        this.columnIndex = columnIndex;
    }

    @Override
    public boolean include(Entry entry)
    {
        switch(columnIndex)
        {
            case 0: return entry.getStringValue(0).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 1: return entry.getStringValue(1).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 2: return entry.getStringValue(2).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 3: return entry.getStringValue(3).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 4: return entry.getStringValue(4).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 5: return entry.getStringValue(5).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 6: return entry.getStringValue(6).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 7: return entry.getStringValue(7).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 8: return entry.getStringValue(8).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 9: return entry.getStringValue(9).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 10: return entry.getStringValue(10).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 11: return entry.getStringValue(11).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 12: return entry.getStringValue(12).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 13: return entry.getStringValue(13).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            case 14: return entry.getStringValue(14).indexOf(text) >= 0; // Вернуть, если вхождение текста есть в введённой строке
            default: return true; // Вернуть, если вхождение текста есть в введённой строке
        }
    }
}
