/**
 * Yumu.com Inc.
 * Copyright (c) 2014-2016 All Rights Reserved.
 */
package com.yumu.hexie.common.util;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import org.apache.http.annotation.Immutable;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tongqian.ni
 * @version $Id: IOUtils.java, v 0.1 2016年1月7日 上午2:38:15  Exp $
 */

@Immutable
public class IOUtils {

    public static void copy(final InputStream in, final OutputStream out) throws IOException {
        byte[] buf = new byte[2048];
        int len;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
    }

    public static void closeSilently(final Closeable closable) {
        if(closable == null){
            return;
        }
        try {
            closable.close();
        } catch (IOException ignore) {
        }
    }

    public static void copyAndClose(final InputStream in, final OutputStream out) throws IOException {
        try {
            copy(in, out);
            in.close();
            out.close();
        } catch (IOException ex) {
            closeSilently(in);
            closeSilently(out);
            // Propagate the original exception
            throw ex;
        }
    }

    public static void copyFile(final File in, final File out) throws IOException {
        RandomAccessFile f1 = new RandomAccessFile(in, "r");
        RandomAccessFile f2 = new RandomAccessFile(out, "rw");
        try {
            FileChannel c1 = f1.getChannel();
            FileChannel c2 = f2.getChannel();
            try {
                c1.transferTo(0, f1.length(), c2);
                c1.close();
                c2.close();
            } catch (IOException ex) {
                closeSilently(c1);
                closeSilently(c2);
                // Propagate the original exception
                throw ex;
            }
            f1.close();
            f2.close();
        } catch (IOException ex) {
            closeSilently(f1);
            closeSilently(f2);
            // Propagate the original exception
            throw ex;
        }
    }

}

