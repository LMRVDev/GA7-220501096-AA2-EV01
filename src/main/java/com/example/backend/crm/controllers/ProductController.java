package com.example.backend.crm.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.crm.models.entities.Product;
import com.example.backend.crm.models.payload.MessageResponse;
import com.example.backend.crm.services.ProductService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 * La clase controlador se encarga de definir los endpoints
 * HTTP para acceder a la lógica de negocio de una entidad,
 * los puntos finales definen las URIs a las que se
 * realizan las peticiones y las operaciones disponibles
 * en un servidor web o API RESTful.
 *
 * @RestController se utiliza para marcar una clase como
 * un controlador de Spring que maneja solicitudes HTTP y
 * produce respuestas HTTP.
 *
 * @RequestMapping se utiliza para definir la estructura
 * de las rutas URL, en este caso todas las rutas
 * definidas en los métodos de este controlador se
 * ejecutan en la ruta base "api/v1/student"
 */
@RestController
@RequestMapping("/products")
@CrossOrigin(originPatterns = "*")
public class ProductController {

    /*
     * @Autowired se utiliza para inyectar instancias de
     * clases de servicio u otros componentes en
     * controladores u otras clases que los necesiten.
     */
    @Autowired
    private ProductService productService;

    /*
     * @GetMapping se usa para mapear solicitudes HTTP GET
     * a un método controlador, en este caso el método
     * showAll() solo manejará solicitudes GET a la ruta
     * /products
     *
     * @ResponseStatus se utiliza para definir el código de
     * estado HTTP que debe devolver un controlador cuando
     * se invoca alguno de sus métodos
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showAll() {

        List<Product> getList = productService.listAll();

        /*
         * MensajeResponse se utiliza para encapsular la
         * respuesta de los métodos del controlador en
         * una estructura estándar.
         */
        if (getList == null) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("No existen registros")
                    .object(null)
                    .build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .object(getList)
                        .build(),
                HttpStatus.OK);
    }

    /*
     * @PostMapping es una anotación que se utiliza para
     * mapear una solicitud HTTP POST a un método.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Product product) {

        try {

            if (productService.existByBarcode(product.getBarcode())) {
                Product productFound = productService.findByBarcode(product.getBarcode());
                return new ResponseEntity<>(MessageResponse.builder()
                        .message("El producto con el código " + product.getBarcode() + " ya se encuentra registrado")
                        .object(productFound.getBarcode())
                        .build(), HttpStatus.CONFLICT);

            } else {

                Product productSave = productService.saveProduct(product);

                return new ResponseEntity<>(MessageResponse.builder()
                        .message("Producto guardado")
                        .object(productSave)
                        .build(), HttpStatus.CREATED);
            }

        } catch (Exception e) {

            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /*
     * @GetMapping("/{id}") se utiliza para mapear solicitudes
     * HTTP GET a un método controlador que maneja la búsqueda
     * de un recurso por ID. El cual se define en el parametro
     * del método con la anotacion @PathVariable
     *
     * @PathVariable se utiliza para vincular un valor que
     * aparece en la URL a un parámetro del método del controlador,
     * permitiendo que el valor se utilice en la lógica del controlador.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> showById(@PathVariable Long id) {
        Product productSave = productService.findById(id);

        if (productSave == null) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("El registro no fue encontrado")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                MessageResponse.builder()
                        .message("")
                        .object(productSave)
                        .build(),
                HttpStatus.OK);
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> filterProduct(@RequestParam String query) {
        List<Product> productsResult = productService.findByName(query);

        if (productsResult.isEmpty()) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Ningún producto coincide con el parametro de búsqueda")
                    .object(null)
                    .build(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(MessageResponse.builder()
                .message("")
                .object(productsResult)
                .build(), HttpStatus.OK);
    }

    /*
     * @DeleteMapping especifica que el método solo manejará
     * las solicitudes que se realicen utilizando el método
     * HTTP DELETE.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {

        try {
            Product productDelete = productService.findById(id);
            productService.deleteProduct(productDelete);

            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Producto eliminado")
                    .object(null)
                    .build(), HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message(e.getMessage())
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /*
     * @PutMapping especifica que el método solo manejará
     * las solicitudes que se realicen utilizando el método
     * HTTP PUT.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Product product,
            @PathVariable Long id) {
        Optional<Product> o = productService.updateProduct(product, id);

        if (o.isPresent()) {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Producto actualizado")
                    .object(o)
                    .build(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(MessageResponse.builder()
                    .message("Producto no encontrado")
                    .object(null)
                    .build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<?> getProductsFile() {
        List<Product> products = productService.findAllProducts();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Products");

            // Crear el estilo de celda para el encabezado
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font font = workbook.createFont();
            font.setColor(IndexedColors.WHITE.getIndex());
            headerStyle.setFont(font);

            // Crear el encabezado
            String[] headers = { "ID", "Name", "Description" };
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Llenar la hoja con datos de productos
            int rowNum = 1;
            for (Product product : products) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getDescription());
            }

            workbook.write(out);
            workbook.close();

            ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=products.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(resource.contentLength())
                    .body(resource);

        } catch (IOException e) {
            return new ResponseEntity<>("Error al generar el archivo Excel", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
