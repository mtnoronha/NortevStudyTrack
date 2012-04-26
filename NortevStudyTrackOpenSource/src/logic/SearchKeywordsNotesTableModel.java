package logic;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import other.Format;

public class SearchKeywordsNotesTableModel extends AbstractTableModel {
    

    private static final long serialVersionUID = 1L;
    private List<Note> rows;
    private List<Note> filtered;
    private String[] colunas = new String[]{
        "Data", "Título","Conteúdo"};

    public SearchKeywordsNotesTableModel() {
        rows = new ArrayList<Note>();
        filtered = new ArrayList<Note>();
    }

    public SearchKeywordsNotesTableModel(List<Note> listaDeNote) {       
        filtered = new ArrayList<Note>();
        if (listaDeNote == null) {
            rows = new ArrayList<Note>();
        } else {
            rows = new ArrayList<Note>(listaDeNote);
        }
        find("");
    }

    public void find(String searching) {
        filtered.clear();
        if (searching == null) {
            searching = "";
        }

        searching = searching.toLowerCase();

        for (Note note : rows) {
            String[] searchingKeyWords;
         
            if (!searching.contains("\"")) {
                searchingKeyWords = searching.split(" ");
            } else {
                searchingKeyWords = new String[1];
                searchingKeyWords[0] = searching;
            }

            for (int i = 0; i < searchingKeyWords.length; i++) {

            
                searchingKeyWords[i] = searchingKeyWords[i].replaceAll("\"", "");
                               
                
                if ((searchingKeyWords[i].equals("")
                        || note.getConteudo().toLowerCase().contains(searchingKeyWords[i].toLowerCase()))) {
                    if (!filtered.contains(note)) {
                        filtered.add(note);
                    }
                }
            }
        }

        fireTableDataChanged();
    }
    
     
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public int getRowCount() {
        return filtered.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    ;

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    ;
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Note Note = filtered.get(rowIndex);


        switch (columnIndex) {
            case 0:
                return Format.sdf.format(Note.getData());
            case 1:
                return Note.getTitulo();
            case 2:
                return Note.getConteudo();
                  
            default:
                throw new IndexOutOfBoundsException("columnIndex out of bounds");
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        //nao preciso implementar!
    }

    ;

    public void setValueAt(Note aValue, int rowIndex) {
        //ta certo.. é do filtered mesmo... OBJETO SAO REFERENCIAS DE MEMORIA...
        //SE ATUALIZAR ESTE, ATUALIZA O OUTRO DA OUTRA LISTA...
        filtered.set(rowIndex, aValue);

        fireTableRowsUpdated(rowIndex, rowIndex);
        find("");
    }

    ;


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    
    public Note getNote(int indiceLinha) {
        return filtered.get(indiceLinha);
    }

    public void addNote(Note m) {

        rows.add(m);


        int ultimoIndice = getRowCount() - 1;
        
        fireTableRowsInserted(ultimoIndice, ultimoIndice);
        find("");
    }
    
    public void addListaDeNote(List<Note> Note) {

        int tamanhoAntigo = getRowCount();

        rows.addAll(Note);

        fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);

    }

    public void limpar() {
        rows.clear();
        fireTableDataChanged();
    }

    public boolean isEmpty() {
        return rows.isEmpty();
    }
    
    public int size(){
        return rows.size();
    }
}

    


