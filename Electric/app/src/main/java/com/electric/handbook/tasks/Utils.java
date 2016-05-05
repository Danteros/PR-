package com.electric.handbook.tasks;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Exception;import java.lang.Override;import java.lang.Runnable;import java.lang.String;import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class Utils
{
    private static final String TAG = "Utils";

    /**
     * create specified folders path
     * @param path path of folders which will be created if not exists
     * @return true if path created successfully, otherwise return false
     */
    public static boolean createFolderPath(String path)
    {
        return new File(path).mkdirs();
    }

    /**
     * get parent folder path for specified file
     * @param path path from which will be taken parent path
     * @return parent path for specified path
     */
    public static String getParentFolderPath(String path)
    {
        File file = new File(path);
        return file.getParent();
    }

    /**
     * copy stream
     * @param input input stream
     * @param output output stream
     */
    public static void copyStream(InputStream input, OutputStream output)
    {
        final int size = 1024;
        try
        {
            byte[] buff = new byte[size];
            int count;
            while((count = input.read(buff, 0, size)) != -1)
            {
                output.write(buff, 0, count);
            }
        }
        catch(Exception e)
        {
            Log.d(TAG + ".copyStream()", e.getMessage());
            e.printStackTrace();
        }
    }

    /** save data from input stream to file */
    public static void saveInputStreamToFile(InputStream stream, String fileName)
    {
        Utils.createFolderPath(Utils.getParentFolderPath(fileName)); // create folder path of necessary

        InputStream input = new BufferedInputStream(stream);
        OutputStream output;
        try
        {
            output = new FileOutputStream(fileName);
        }
        catch(FileNotFoundException e)
        {
            Log.d(TAG + ".saveInputStreamToFile()", "FileNotFoundException");
            e.printStackTrace();
            return;
        }

        byte data[] = new byte[1024];
        int count;
        try
        {
            while((count = input.read(data)) != -1)
            {
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            // input.close();
        }
        catch(IOException e)
        {
            Log.d(TAG + ".saveInputStreamToFile()", "IOException");
            e.printStackTrace();
        }
    }

    public static void closeInputStream(InputStream stream)
    {
        try
        {
            stream.close();
        }
        catch(IOException e)
        {
            Log.d(TAG + ".closeInputStream()", "stream.close() IOException");
            e.printStackTrace();
        }
    }

    /**
     * read file hash and compare it to specified hash
     * @param fileName filename of file to be checked
     * @param hash correct hash for file, specified in fileName
     * @param hashType the name of the algorithm to use(MD5 for example)
     * @return true if hashes match, otherwise or if file cannot be readed for some reason - false
     */
    public static boolean isFileHashMatch(String fileName, String hash, String hashType)
    {
        Log.d(TAG + ".isFileHashMatch()", "enter");

        InputStream input = null;
        try
        {
            input = new FileInputStream(fileName);
        }
        catch(FileNotFoundException e)
        {
            Log.d(TAG + ".isFileHashMatch()", "file doesn't exists");
            return false;
        }

        boolean isHashMatch = isInputStreamHashMatch(input, hash, hashType);

        try
        {
            if(input != null)
                input.close();
        }
        catch(IOException e)
        {
            Log.d(TAG + ".isFileHashMatch()", "IOException: input.close()");
            e.printStackTrace();
        }

        return isHashMatch;
    }

    private static boolean isInputStreamHashMatch(InputStream input, String hash, String hashType)
    {
        MessageDigest messageDigest = null;
        try
        {
            messageDigest = MessageDigest.getInstance(hashType);
        }
        catch(NoSuchAlgorithmException e)
        {
            Log.d(TAG + ".isInputStreamHashMatch()", "NoSuchAlgorithmException");
            return false;
        }

        input = new DigestInputStream(input, messageDigest);
        try
        {
            byte buff[] = new byte[1024];
            while(input.read(buff) != -1)
            {
                // do nothing. just read file so MessageDigest can calculate hash
            }
        }
        catch(IOException e)
        {
            Log.d(TAG + ".isInputStreamHashMatch()", e.getMessage());
            e.printStackTrace();
            return false;
        }
        byte[] digest = messageDigest.digest();

        return hash.equals(digest);
    }

    public static Calendar resetTime(Calendar calendar)
    {
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static void showToast(final Context context, final int textId, final int duration)
    {
        if(context == null)
            return;

        ((Activity)context).runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(context, textId, duration).show();
            }
        });
    }

    public static void showToast(final Context context, final String text, final int duration)
    {
        if(context == null)
            return;

        ((Activity)context).runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(context, text, duration).show();
            }
        });
    }

}
