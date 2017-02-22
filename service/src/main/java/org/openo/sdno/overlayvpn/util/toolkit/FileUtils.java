/*
 * Copyright 2017 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.overlayvpn.util.toolkit;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The tool class to deal with files.<br>
 * 
 * @author
 * @version SDNO 0.5 Jan 24, 2017
 */
public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    private static final String FILE_DEFAULT_CHART = "UTF-8";

    private FileUtils() {

    }

    /**
     * Get file list.<br>
     * 
     * @param path The file path
     * @param fileType The file type
     * @return The file list
     * @since SDNO 0.5
     */
    public static File[] getFiles(final String path, final String fileType) {
        File file = new File(path);
        if(file.exists() && file.isDirectory()) {
            File[] files = file.listFiles(new FileFilter() {

                @Override
                public boolean accept(File file) {
                    return file.getName().endsWith(fileType);
                }
            });
            if(files != null) {
                return files;
            }
        }
        return new File[0];
    }

    /**
     * Read file.<br>
     * 
     * @param file The file object
     * @return The file contents
     * @since SDNO 0.5
     */
    public static String readFile(File file) {
        BufferedReader reader = null;
        FileInputStream fileInputStream = null;
        StringBuilder fileContent = new StringBuilder();
        try {
            fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, FILE_DEFAULT_CHART);
            reader = new BufferedReader(inputStreamReader);
            LineIterator lineIr = new LineIterator(reader);
            while(lineIr.hasNext()) {
                String lineString = lineIr.next();
                if(lineString == null) {
                    break;
                }
                fileContent.append(lineString);
            }
        } catch(IOException e) {
            LOGGER.error("ReadFile failed!", e);
        } finally {
            closeFileStreamNotThrow(fileInputStream);
            closeFileStreamNotThrow(reader);
        }
        return fileContent.toString();
    }

    private static void closeFileStreamNotThrow(Closeable fis) {
        if(fis != null) {
            try {
                fis.close();
            } catch(IOException e) {
                LOGGER.error("Close reader failed!", e);
            }
        }
    }
}
