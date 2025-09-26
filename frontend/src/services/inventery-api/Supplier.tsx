import api from "./api";

type SupplierResonseDto = any;

type PageParams = {
  search?: string | null;
  status?: string | null;
  page?: number;
  size?: number;
  sort?: string;
}

// Crud
export const createSupplier = (data: SupplierResonseDto) => api.post(`/api/supplier`);
export const updateSupplier = (id: string, data: SupplierResonseDto) => api.put(`/api/supplier/${id}`, data);
export const deleteSupplier = (id: string) => api.delete(`/api/supplier/${id}`);
export const getSuppliers = (id: string) => api.get(`/api/supplier/${id}`);

// Search 
export const getSearchSupplier = ({
  search = null,
  status = null,
  page = 0,
  size = 5,
  sort = 'name,asc'
} : PageParams) => api.get(`/api/supplier/search`, {
  params: {search, status, page, size, sort},
});