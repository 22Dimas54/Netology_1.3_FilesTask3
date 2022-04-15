package ru.netology.honeybadger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    private static String path1 = "G://Games/savegames/save1.dat";
    private static String path2 = "G://Games/savegames/save2.dat";
    private static String path3 = "G://Games/savegames/save3.dat";

    public static void main(String[] args) {
//1. Произвести распаковку архива в папке savegames.
        openZip("G://Games/savegames/save1.dat.zip", path1);
        openZip("G://Games/savegames/save2.dat.zip", path2);
        openZip("G://Games/savegames/save3.dat.zip", path3);
//2. Произвести считывание и десериализацию одного из разархивированных файлов save.dat.
        GameProgress gameProgress1 = openProgress(path1);
        GameProgress gameProgress2 = openProgress(path2);
        GameProgress gameProgress3 = openProgress(path3);
//3. Вывести в консоль состояние сохранненой игры.
        System.out.println(gameProgress1.toString());
        System.out.println(gameProgress2.toString());
        System.out.println(gameProgress3.toString());
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
