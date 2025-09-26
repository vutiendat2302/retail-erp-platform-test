import api from "./api";

type ImportProductLogRequestDto = any;
type HistoryPayDto = any;
type ProductBatchDto = any;
type PageParams = {
  search?: string | null;
  status?: string | null;
  page?: number;
  size?: number;
  sort?: string;
}
/**
 * Tạo một đơn nhập hàng mới (gồm log và danh sách sản phẩm)
 * Tương ứng với: @PostMapping("/createImportLog")
 */
export const createImportLog = (data: ImportProductLogRequestDto) => api.post('/api/importProductLog/createImportLog', data);

/**
 * Lấy thông tin chi tiết của một đơn nhập hàng
 * Tương ứng với: @GetMapping("/getImportLog/{id}")
 */
export const getImportLog = (id: number | string) => api.get(`/api/importProductLog/getImportLog/${id}`);

/**
 * Lấy danh sách các sản phẩm trong một đơn nhập hàng
 * Tương ứng với: @GetMapping("/getImportProductList/{logId}")
 */
export const getImportProductList = (logId: number | string) => api.get(`/api/importProductLog/getImportProductList/${logId}`);

/**
 * Cập nhật trạng thái của một đơn nhập hàng
 * Tương ứng với: @PutMapping("/updateImportLog/{importLogId}")
 */
export const updateImportLogStatus = (importLogId: number | string, status: 'active' | 'inactive') =>
api.put(`/api/importProductLog/updateImportLog/${importLogId}`, null, { params: { status } });

/**
 * Tạo một hóa đơn thanh toán cho đơn nhập hàng
 * Tương ứng với: @PostMapping("/createHistoryPay/{logId}")
 */
export const createHistoryPay = (logId: number | string, data: HistoryPayDto) =>
  api.post(`/api/importProductLog/createHistoryPay/${logId}`, data);

/**
 * Lấy lịch sử thanh toán của một đơn nhập hàng
 * Tương ứng với: @GetMapping("/getHistoryPay/{logId}")
 */
export const getHistoryPay = (logId: number | string) =>
  api.get(`/api/importProductLog/getHistoryPay/${logId}`);


export const createProductBatch = (data: ProductBatchDto) =>
  api.post('/api/importProductLog/createProductBatch', data);


/**
 * Lấy về danh sách nhà cung cấp
 * Tương ứng với: @GetMapping("/suppliers")
 */
export const getSuppliers = () => api.get('/api/supplier/suppliers');


/**
 * Lấy về danh sách kho hàng
 * Tương ứng với: @GetMapping("/warehouses")
 */
export const getWarehouses = () => api.get('/api/warehouse/warehouses');


/**
 * Lấy về danh sách sản phẩm (để chọn)
 * Tương ứng với: @GetMapping("/products")
 */
export const getProducts = () => api.get('/api/product/products');


export const getSearchImport = ({
  search = null,
  status = null,
  page = 0,
  size = 5,
  sort = 'name,asc',
} : PageParams) => api.get(`/api/importProductLog/import-logs`, {
  params: {search, status, page, size, sort}
})


export const deleteProduct = (id: string) => api.delete(`/api/importProductLog/deleteImport/${id}`);
