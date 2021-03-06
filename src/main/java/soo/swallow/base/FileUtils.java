/*
 * Copyright 2015 Soo [154014022@qq.com | sootracker@gmail.com]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package soo.swallow.base;

import java.io.File;
import java.io.IOException;

/**The kit of file
 * Created by Soo.
 */
public class FileUtils {

    private static final String TAG = "FileUtils--->";

    /**Create a file by particular directory file and particular name, the file is not directory
     * @param directory The directory of the file which to be created
     * @param name The name of file
     * @param flag If true, always create a new file;otherwise create a new file if the file is not exist
     * @return The file which has been created success, null if failed to be created
     * @see #createFile(String, boolean)
     * @see #createFile(File, boolean)
     */
    public static File createFile(File directory, String name, boolean flag) {
        ArgsUtils.notNull(directory, "The directory of file");
        ArgsUtils.notNull(name, "The name of file");
        File file = new File(directory, name);
        if (createFile(file, flag)) {
            return file;
        }
        return null;
    }

    /**Create a file by particular path,the file is not directory
     * @param filePath The path of particular file which to be created
     * @param flag If true, always create a new file;otherwise create a new file if the file is not exist
     * @return The file which has been created success, null if failed to be created
     * @see #createFile(File, boolean)
     */
    public static File createFile(String filePath, boolean flag) {
        ArgsUtils.notNull(filePath, "File path");
        File file = new File(filePath);
        if (createFile(file, flag)) {
            return file;
        }
        return null;
    }

    /**Create a file.Note,the file is not directory
     * @param file The file to be created
     * @param flag If true, always create a new file;otherwise create a new file if the file is not exist
     * @return True if create success, otherwise return false
     */
    public static boolean createFile(File file, boolean flag) {
        ArgsUtils.notNull(file, "File is null");
        if (file.isDirectory()) {
            return false;
        }
        if (flag) {
            file.deleteOnExit();
        }
        if (!file.exists()) {
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file.exists();
    }

    /**Create a directory file
     * @param file The file to be created
     * @param flag If true, always create a new directory;otherwise create a new directory if the file is not exist
     * @return True if create success, otherwise return false
     */
    public static boolean createDir(File file, boolean flag) {
        ArgsUtils.notNull(file, "File is null");
        if (flag && file.isDirectory()) {
            file.deleteOnExit();
        }
        return file.mkdirs();
    }

}
