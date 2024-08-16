//package com.shopping.store.service.Excel;
//
//import com.shopping.store.dto.ProductDto;
//import com.shopping.store.entity.Category;
//import com.shopping.store.entity.Product;
//import com.shopping.store.repo.CategoryRepository;
//import lombok.AllArgsConstructor;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigDecimal;
//import java.util.*;
//
//@Service
//@AllArgsConstructor
//public class ExcelUploadService {
//
//    private final CategoryRepository categoryRepository;
//
//    public static boolean isValidExcelFile(MultipartFile file) {
//        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//    }
//
//    public List<Product> getProductsDataFromExcel(InputStream inputStream,Product request) throws IOException {
//        List<Product> products = new ArrayList<>();
//        try {
//            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//            XSSFSheet sheet = workbook.getSheetAt(0);
//            int rowIndex = 0;
//            for (Row row : sheet) {
//                if (rowIndex == 0) {
//                    rowIndex++;
//                    continue;
//                }
//                Product product = new Product();
//                Iterator<Cell> cellIterator = row.iterator();
//                int cellIndex = 0;
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    switch (cellIndex) {
//                        case 0 -> product.setName(cell.getStringCellValue());
//                        case 1 -> product.setBrand(cell.getStringCellValue());
//                        case 2 -> product.setPrice(BigDecimal.valueOf(cell.getNumericCellValue()));
//                        case 3 -> product.setInventory((int) cell.getNumericCellValue());
//                        case 4 -> product.setDescription(cell.getStringCellValue());
//                        case 5 -> {
//
//                            String categoryName = cell.getStringCellValue();
//                            Category category = categoryRepository.findByName(categoryName)
//                                    .orElseGet(() -> {
//                                        Category newCategory = new Category(categoryName);
//                                        return categoryRepository.save(newCategory);
//                                    });
//                            product.setCategory(category);
//                        }
//                        default -> {
//                        }
//                    }
//                    cellIndex++;
//                }
//                products.add(product);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw e;
//        }
//        return products;
//    }
//}