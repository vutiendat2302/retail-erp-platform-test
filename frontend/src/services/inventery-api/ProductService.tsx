import api from "./api";
import { getBrands } from "./BrandService";

type ProductResponseDto = any;

interface PageParams {
    page?: number;
    size?: number;
    sort?: string;
}

// CRUD
export const createProduct = (data: ProductResponseDto) => api.post('/api/product', data);

export const getProducts = () => api.get('/api/product');

export const getProduct = (id: string) => api.get(`/api/product/${id}`);

export const updateProduct = (id: string, data: ProductResponseDto) => api.put(`/api/product/${id}`, data);

export const deleteProduct = (id: string) => api.delete(`/api/product/${id}`);

export const fetchAllBrands = () => getBrands();


// Phân trang

export const getPageProducts = ({
    page = 0,
    size = 5,
    sort = 'name,asc',
}: PageParams= {}) => api.get('/api/product/page', {
    params: {page, size, sort},
});