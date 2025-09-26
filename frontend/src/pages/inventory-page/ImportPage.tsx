// file: pages/inventory-page/ImportPage.tsx
import React, { useState, useEffect, useCallback } from 'react';
import { PlusCircle } from 'lucide-react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { getImportLogs, createImportLog, updateImportLog, deleteImport } from '@/services/api'; // Giả sử api service
import { CreateImportDialog } from '@/components/inventory/CreateImportDialog';
import { ImportLogTable } from '@/components/inventory/ImportLogTable';
import { Input } from '@/components/ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';

// Định nghĩa kiểu dữ liệu cho một đơn hàng
interface ImportLog {
  id: string | number;
  fromSupplierName: string;
  toWarehouseName: string;
  totalAmount: number;
  status: boolean;
  createAt: string;
  [key: string]: any;
}

const ImportPage: React.FC = () => {
    const [logs, setLogs] = useState<ImportLog[]>([]);
    const [totalPages, setTotalPages] = useState<number>(0);
    const [loading, setLoading] = useState<boolean>(false);
    const [isDialogOpen, setIsDialogOpen] = useState<boolean>(false);
    const [currentLog, setCurrentLog] = useState<ImportLog | null>(null);

    const [filters, setFilters] = useState({
        search: '',
        status: null as 'active' | 'inactive' | null,
        page: 0,
        size: 10,
    });

    const fetchLogs = useCallback(async () => {
        setLoading(true);
        try {
            const response = await getImportLogs(filters);
            setLogs(response.data.content);
            setTotalPages(response.data.totalPages);
        } catch (error) {
            console.error("Lỗi khi tải đơn hàng:", error);
        } finally {
            setLoading(false);
        }
    }, [filters]);

    useEffect(() => {
        fetchLogs();
    }, [fetchLogs]);

    const handleSuccess = () => {
        setIsDialogOpen(false);
        fetchLogs();
    };

    const handleCreate = () => {
        setCurrentLog(null);
        setIsDialogOpen(true);
    };

    const handleEdit = (log: ImportLog) => {
        setCurrentLog(log);
        setIsDialogOpen(true);
    };

    const handleDelete = async (id: string | number) => {
        if (!window.confirm('Xác nhận xóa đơn nhập hàng này?')) return;
        try {
            await deleteImport(id);
            fetchLogs(); // Tải lại dữ liệu
        } catch (error) {
            console.error("Lỗi khi xóa:", error);
        }
    };
    
    const handleFilterChange = (key: string, value: any) => {
        setFilters(prev => ({ ...prev, [key]: value, page: 0 }));
    };
      
    const handlePageChange = (newPage: number) => {
        setFilters(prev => ({ ...prev, page: newPage }));
    };

    return (
        <div className="p-4 md:p-6 lg:p-8 space-y-6">
            <Card>
                <CardHeader className="flex flex-row items-center justify-between">
                    <div>
                        <CardTitle>Quản lý nhập hàng</CardTitle>
                        <p className="text-sm text-muted-foreground">Theo dõi và quản lý các đơn hàng đã nhập.</p>
                    </div>
                    <Button onClick={handleCreate}>
                        <PlusCircle className="mr-2 h-4 w-4" />
                        Tạo đơn nhập hàng
                    </Button>
                </CardHeader>
                <CardContent className="space-y-4">
                    <div className="flex items-center gap-4">
                        <Input 
                            placeholder="Tìm kiếm..."
                            value={filters.search}
                            onChange={(e) => handleFilterChange('search', e.target.value)}
                            className="max-w-sm"
                        />
                        <Select onValueChange={(value) => handleFilterChange('status', value === 'all' ? null : value)}>
                            <SelectTrigger className="w-[180px]"><SelectValue placeholder="Lọc trạng thái" /></SelectTrigger>
                            <SelectContent>
                                <SelectItem value="all">Tất cả</SelectItem>
                                <SelectItem value="active">Hoàn thành</SelectItem>
                                <SelectItem value="inactive">Chờ xử lý</SelectItem>
                            </SelectContent>
                        </Select>
                    </div>
                    <ImportLogTable 
                        logs={logs} 
                        loading={loading} 
                        onEdit={handleEdit}
                        onDelete={handleDelete}
                    />
                    {/* Thêm component Pagination ở đây nếu cần */}
                </CardContent>
            </Card>

            <CreateImportDialog
                isOpen={isDialogOpen}
                onOpenChange={setIsDialogOpen}
                onSuccess={handleSuccess}
                initialData={currentLog}
            />
        </div>
    );
};

export default ImportPage;