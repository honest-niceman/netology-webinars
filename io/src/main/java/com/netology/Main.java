package com.netology;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

//https://github.com/netology-code/jd-homeworks/blob/master/files/README.md
public class Main {
    public static final String FILE_NAME = "notes.txt";
    public static final String ZIP_NAME = "zip_out.zip";
    public static final String LARGE_FILE = "java.pdf";
    public static final String LARGE_FILE_COPY = "java_copy.pdf";

    public static void main(String[] args) {
        byteBasedIOStreams();
        differenceInTime();
        characterBasedIOStreams();
        zips();
        serialization();
    }

    public static void serialization() {
        serialize();
        deserialize();
    }

    public static void serialize() {
        GameProgress gameProgress = new GameProgress(1,2,3,4);
        try (FileOutputStream fos = new FileOutputStream("save.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)
        ){
            oos.writeObject(gameProgress);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deserialize() {
        try (FileInputStream fis = new FileInputStream("save.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)
        ){
            Object o = ois.readObject();
            if (o instanceof GameProgress gameProgress) {
                System.out.println(gameProgress);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zips() {
        zipOutputStream();
        zipInputStream();
    }

    public static void characterBasedIOStreams() {
        fileWriter();
        fileReader();
        bufferedFileWriter();
        bufferedFileReader();
    }

    public static void byteBasedIOStreams() {
        outputExampleBeforeJava7();
        inputExampleBeforeJava7();
        File file = new File(FILE_NAME);
        if (file.delete()) System.out.println("File was deleted.");
        outputExampleAfterJava7();
        inputExampleAfterJava7();
    }

    public static void differenceInTime() {
        File file = new File(LARGE_FILE);
        File copy = new File(LARGE_FILE_COPY);
        simpleIOStreamsTest(file, copy);
        customIOStreamsTest(file, copy);
        bufferedIOStreamsTest(file, copy);
    }

    public static void outputExampleBeforeJava7() {
        FileOutputStream fos = null;
        try {
            String hello = "Hello";
            fos = new FileOutputStream(FILE_NAME);
            fos.write(hello.getBytes());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void inputExampleBeforeJava7() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(FILE_NAME);
            StringBuilder builder = new StringBuilder();

            int b;
            while ((b = fis.read()) != -1) {
                builder.append((char) b);
            }

            System.out.println(builder);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void outputExampleAfterJava7() {
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
            String hello = "Hello";
            fos.write(hello.getBytes());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void inputExampleAfterJava7() {
        try (FileInputStream fis = new FileInputStream(FILE_NAME)) {
            StringBuilder builder = new StringBuilder();

            int b;
            while ((b = fis.read()) != -1) {
                builder.append((char) b);
            }

            System.out.println(builder);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void simpleIOStreamsTest(File originalPath, File copyPath) {
        TimeExecution.measureExecutionTime(() -> {
            try (
                    FileInputStream fis = new FileInputStream(originalPath);
                    FileOutputStream fos = new FileOutputStream(copyPath)
            ) {
                int byteAsInt;
                while ((byteAsInt = fis.read()) != -1) {
                    fos.write(byteAsInt);
                }
            }
        });
        copyPath.deleteOnExit();
    }

    public static void customIOStreamsTest(File originalPath, File copyPath) {
        TimeExecution.measureExecutionTime(() -> {
            try (
                    FileInputStream fis = new FileInputStream(originalPath);
                    FileOutputStream fos = new FileOutputStream(copyPath)
            ) {
                byte[] buffer = new byte[1024 * 5]; //5kB
                while (fis.read(buffer) != -1) {
                    fos.write(buffer);
                }
            }
        });
        copyPath.deleteOnExit();
    }

    public static void bufferedIOStreamsTest(File originalPath, File copyPath) {
        TimeExecution.measureExecutionTime(() -> {
            try (
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(originalPath));
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(copyPath))
            ) {
                int byteAsInt;

                while ((byteAsInt = bis.read()) != -1) {
                    bos.write(byteAsInt);
                }
            }
        });
        copyPath.deleteOnExit();
    }

    public static void fileWriter() {
        String s = "FileWriter and FileReader example";
        try (FileWriter fileWriter = new FileWriter(FILE_NAME)) {
            fileWriter.write(s);
            fileWriter.append("......");
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void bufferedFileWriter() {
        String s = "BufferedFileWriter and BufferedFileReader example";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME))) {
            bufferedWriter.write(s);
            bufferedWriter.append("......");
            bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void fileReader() {
        try (FileReader fileReader = new FileReader(FILE_NAME)) {
            int byteAsInt;
            while ((byteAsInt = fileReader.read()) != -1) {
                System.out.print((char) byteAsInt);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void bufferedFileReader() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipOutputStream() {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(ZIP_NAME));
             BufferedInputStream bis = new BufferedInputStream(new FileInputStream(FILE_NAME))
        ) {
            zos.putNextEntry(new ZipEntry("packed_" + FILE_NAME));
            byte[] buffer = new byte[bis.available()];
            bis.read(buffer);
            zos.write(buffer);
            zos.closeEntry();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipInputStream() {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(ZIP_NAME))) {
            ZipEntry zipEntry;
            String name;
            while ((zipEntry = zis.getNextEntry()) != null) {
                name = zipEntry.getName();
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(name))){
                    for (int i = zis.read(); i != -1 ; i = zis.read()) {
                        bos.write(i);
                    }
                }
                zis.closeEntry();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
