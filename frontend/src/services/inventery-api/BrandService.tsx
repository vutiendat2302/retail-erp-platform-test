import api from "./api";

type BrandResponseDto = any;

type PageParams = {
  search?: string | null;
  status?: string | null;
  page?: number;
  size?: number;
  sort?: string;
}

// CRUD
export const createBrand = (data: BrandResponseDto) => api.post(`api/brand`, data);
export const updateBrand = (id: string, data: BrandResponseDto) => api.put(`api/brand/${id}`, data);
export const getBrand = (id: string) => api.get(`api/brand${id}`);
export const deleteBrand = (id: string) => api.delete(`api/brand/${id}`);

export const getSearchBrand = ({
  search = null,
  status = null,
  page = 0,
  size = 5,
  sort = 'name,asc',
} : PageParams) => api.get(`api/brand/search`, {
  params: {search, status, page, size, sort}
})