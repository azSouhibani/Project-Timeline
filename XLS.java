package de;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;



public class XLS {


    projectList pl = new projectList();

    stageList sl = new stageList();

    public projectList readProject(String projectPathName) throws IOException {

        FileInputStream fis = new FileInputStream(new File(projectPathName));

        HSSFWorkbook myWorkBook = new HSSFWorkbook (fis);

        HSSFSheet mySheet = myWorkBook.getSheetAt(0);


        Iterator<Row> rowIterator = mySheet.iterator();

        String[] storeInfo = new String[9];

        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
//			 to skip the header
            if (row.getRowNum()==0)
                continue;

            for (int i = 0; i<9; i++) {

                if (row.getCell(i) == null )
                    storeInfo[i] = null;
                else
                    storeInfo[i] = row.getCell(i).toString();
            }

            pl.addProject(storeInfo);
        }

        myWorkBook.close();

        return pl;

    }

    public stageList readStage(String stagePath, String stgDetPath) throws IOException {

        FileInputStream f1 = new FileInputStream(new File(stagePath));

        FileInputStream f2 = new FileInputStream(new File(stgDetPath));

        HSSFWorkbook wb1 = new HSSFWorkbook (f1);
        HSSFWorkbook wb2 = new HSSFWorkbook (f2);

        HSSFSheet sheet1 = wb1.getSheetAt(0);
        HSSFSheet sheet2 = wb2.getSheetAt(0);

        Iterator<Row> rowItr1 = sheet1.iterator();
        String stageInfo[] = new String[7];

        Iterator<Row> rowItr2 = sheet2.iterator();
        String stageDetInfo[] = new String[5];

        while (rowItr1.hasNext()) {
            Row row1 = rowItr1.next();
            Row row2 = rowItr2.next();

//			 to skip the header
            if (row1.getRowNum()==0)
                continue;
//			 0 1 2 3 4 5 6
            for (int i = 0; i<7 ; i++) {

                if (row1.getCell(i) == null)
                    stageInfo[i] = null;
                else
                    stageInfo[i] = row1.getCell(i).toString(); // stage file

                if ( i < 5 && row2.getCell(i) == null)
                    stageDetInfo[i] = null;

                else if (i < 5)
                    stageDetInfo[i] = row2.getCell(i).toString(); // stage_details file
            }

            sl.addComStage(stageInfo, stageDetInfo);


        }

        wb1.close();
        wb2.close();

        return sl;
    }

}



