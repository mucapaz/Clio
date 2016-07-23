package cs190i.cs.ucsb.edu.pazspm.clio;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import cs190i.cs.ucsb.edu.pazspm.clio.connection.JSONUtils;


public class ImageStorageWrapper {

    private File folder;

    int imageCounter;

    public ImageStorageWrapper(){
        folder = getAlbumStorageDir("/ClioPics/");
        imageCounter = generateImageCounter("imageCounter.txt");

    }

    public ArrayList<Bitmap> generateBitmapFromStorage(int number){
        int count = 0;
        ArrayList<Bitmap> ret = new ArrayList<Bitmap>();
        for(int i = imageCounter-1; i>= 0;i--){
            if(count == number) break;

            Bitmap bmapOrig = BitmapFactory.decodeFile(folder + "/" + i + ".jpg");
            int width = bmapOrig.getWidth()/12;
            int height = bmapOrig.getHeight()/12;
            Bitmap bmap = JSONUtils.ShrinkBitmap(folder + "/" + i + ".jpg", width, height);
            ret.add(bmap);
            count++;
        }

        return ret;
    }

    public int generateImageCounter(String imageCounterFilePath) {
        File f = new File(folder, imageCounterFilePath);

        if (f.exists()) { // EXISTENT FILE
            String line = "";
            try {
                FileInputStream fileInputStream = new FileInputStream(f);
                System.out.println(f);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                line = bufferedReader.readLine();

                bufferedReader.close();
                fileInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  Integer.parseInt(line);
        } else { // FILE CREATE

            try {
                f.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(f);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                bufferedWriter.write("0");

                bufferedWriter.close();
                fileOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return 0;
        }


    }

    public void saveUserImage(byte[] data) {
        //create a file to write bitmap data
        File f = new File(folder, String.format("%d.jpg", imageCounter++));
        File f1 = new File(folder, "imageCounter.txt");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(data);
            fos.flush();
            fos.close();

            fos = new FileOutputStream(f1);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
            bufferedWriter.write(imageCounter + "");
            bufferedWriter.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(Environment.getExternalStorageDirectory(), albumName);

        if (file.exists()) {
            System.out.println("Existent folder");
        } else if (!file.mkdir()) {
            System.out.println("Couldn't create folder");
        } else {
            System.out.println("Folder Created");
        }

        return file;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
