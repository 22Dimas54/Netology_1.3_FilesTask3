package ru.netology.honeybadger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    private static List<String> arrayPath = Arrays.asList("G://Games/savegames/save1.dat",
            "G://Games/savegames/save2.dat",
            "G://Games/savegames/save3.dat");
    private static List<GameProgress> arrayGameProgress = new ArrayList<>();

    public static void main(String[] args) {

        for (int i = 0; i < arrayPath.size(); i++) {
            //1. Произвести распаковку архива в папке savegames.
            openZip(arrayPath.get(i) + ".zip", arrayPath.get(i));
            //2. Произвести считывание и десериализацию одного из разархивированных файлов save.dat.
            arrayGameProgress.add(openProgress(arrayPath.get(i)));
            //3. Вывести в консоль состояние сохранненой игры.
            System.out.println(arrayGameProgress.get(i).toString());
        }
    }

    private static GameProgress openProgress(String path) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return gameProgress;
    }

    private static void openZip(String pathZipFile, String pathFile) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(pathZipFile))) {
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                FileOutputStream fout = new FileOutputStream(pathFile);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
