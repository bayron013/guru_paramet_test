package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import guru.qa.dto.ResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestParsingFiles {

    private ClassLoader cl = TestParsingFiles.class.getClassLoader();

    @Test
    void zipFileParsing() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("test_data/test ZIP.zip"))) {
            ZipEntry entry;
            List<String> expectedFileNames = List.of("test csv.csv", "test pdf.pdf", "test excel.xlsx");
            List<String> actualFileNames = new ArrayList<>();
            while ((entry = zis.getNextEntry()) != null) {
                actualFileNames.add(entry.getName());
            }

            assertEquals(new HashSet<>(expectedFileNames), new HashSet<>(actualFileNames),
                    "Файлы в архиве не соответствуют ожидаемым");
        }
    }

    @Test
    void xlsFileParsing() throws Exception {
        XLS xls = null;
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("test_data/test ZIP.zip"))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                if (fileName.equals("test excel.xlsx")) {
                    xls = new XLS(new ByteArrayInputStream(zis.readAllBytes()));
                }
            }

            String actualValue = xls.excel.getSheetAt(0).getRow(10).getCell(3).getStringCellValue();
            Assertions.assertEquals("тут содежится текст для тестовой проверки", actualValue);
        }

    }

    @Test
    void pdfFileParsing() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("test_data/test ZIP.zip"))) {
            ZipEntry entry;
            PDF pdf = null;
            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                if (fileName.equals("test pdf.pdf")) {
                    pdf = new PDF(new ByteArrayInputStream(zis.readAllBytes()));
                }
            }
            String actualValue = pdf.text;
            assertTrue(actualValue.contains("Это документ в формате PDF, который был создан для тестирования загрузки файлов"));
        }

    }

    @Test
    void csvFileParsing() throws Exception {
        try (ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("test_data/test ZIP.zip"))) {
            ZipEntry entry;
            CSVReader csvReader;
            List<String[]> rows = null;
            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                if (fileName.equals("test csv.csv")) {
                    byte[] data = zis.readAllBytes();
                    csvReader = new CSVReader(new InputStreamReader(new ByteArrayInputStream(data)));
                    rows = csvReader.readAll();
                }
            }
            assertEquals(7, rows.size());
            assertArrayEquals(new String[]{"QuotaAmount", "StartDate", "OwnerName", "Username"}, rows.get(0));
        }

    }

    @Test
    void jsonFileParsing() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try (Reader reader = new InputStreamReader(cl.getResourceAsStream("test_data/test json.json"))) {
            ResponseDto dto = mapper.readValue(
                    cl.getResourceAsStream("test_data/test json.json").readAllBytes(),
                    ResponseDto.class
            );
            ResponseDto dto1 = mapper.readValue(reader, ResponseDto.class);

            assertNotNull(dto);
            assertNotNull(dto1);
            assertEquals(dto.user.id, 101);
            assertEquals(dto1.user.isActive, true);
            assertEquals(dto.user.roles, Arrays.asList("admin", "editor"));
            assertEquals(dto1.user.profile.email, "john.doe@example.com");
        }

    }


}
