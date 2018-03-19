package org.academiadecodigo.bootcamp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class WordReader implements Iterable<String> {

    private String filename;

    public WordReader(String filename) {
        this.filename = filename;
    }


    public static void openRead(String filename) throws IOException {
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(filename));
        } catch (IOException exception) {
            System.out.println("File exception" + exception);
        }

    }


    @Override
    public Iterator<String> iterator() {
        return new WordReaderIterator();
    }

    private class WordReaderIterator implements Iterator<String> {

        private BufferedReader inputBufferedReader;
        private String currentLine;
        private String[] words;
        private int wordsIndex;


        //contrutor
        public WordReaderIterator() {

            try {      //try catch pra lidar com excepçao

                inputBufferedReader = new BufferedReader(new FileReader(filename));
                currentLine = readLineOfText();
                words = getLineWords(currentLine);  //passar current line ao metodo

            } catch (FileNotFoundException e) {    //catch a excepçao pra nao lixar a cena
                e.printStackTrace();
            }
        }



        private String readLineOfText() {
            String line = null;

            try {
                line = inputBufferedReader.readLine();

                //end of file
                if(line == null){
                    inputBufferedReader.close(); // se a linha for nula, nothing more to do, close it
                    return null;
                }

                //line contains no words
                if(line.equals("")){
                    return readLineOfText();
                }

            } catch (IOException e) {       // excepçao IO, o try catch previne cenas
                e.printStackTrace();
                currentLine = null; // iterator wont return any elements
            }

            return line;
        }

        private String[] getLineWords(String line) { //retorna array the string
            return line != null ? line.split("\\W+") : new String[0]; // o W separa palavras de caracteres(vais buscar palavras)
        }


        @Override
        public boolean hasNext() {

            return currentLine != null; //nao precisa if porq ja retorna boolean
        }

        @Override
        public String next() {

            if(!hasNext()){
                throw new NoSuchElementException();
            }

            String result = words[wordsIndex];  //ir buscar as palavras ao array
            wordsIndex++;

            if(wordsIndex == words.length){
                currentLine = readLineOfText();
                words = getLineWords(currentLine);
                wordsIndex = 0;
            }

            return result;
        }
    }
}