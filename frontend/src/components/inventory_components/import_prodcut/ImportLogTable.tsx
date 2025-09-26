// file: components/inventory/ImportLogTable.tsx
import React from 'react';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { MoreHorizontal } from 'lucide-react';
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuTrigger } from "@/components/ui/dropdown-menu";
import { formatCurrency, formatDate } from '@/utils/formatters'; // File chứa hàm format

// Định nghĩa kiểu dữ liệu
interface ImportLog {
  id: string | number;
  fromSupplierName: string;
  toWarehouseName: string;
  totalAmount: number;
  status: boolean;
  createAt: string;
}
interface ImportLogTableProps {
  logs: ImportLog[];
  loading: boolean;
  onEdit: (log: ImportLog) => void;
  onDelete: (id: string | number) => void;
}

export const ImportLogTable: React.FC<ImportLogTableProps> = ({ logs, loading, onEdit, onDelete }) => {
  if (loading) return <p className="text-center py-4">Đang tải danh sách...</p>;

  return (
    <div className="border rounded-md">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead className="w-[100px]">Mã đơn</TableHead>
            <TableHead>Nhà cung cấp</TableHead>
            <TableHead>Kho nhập</TableHead>
            <TableHead className="text-right">Tổng tiền</TableHead>
            <TableHead className="text-center">Trạng thái</TableHead>
            <TableHead>Ngày tạo</TableHead>
            <TableHead className="text-right">Chức năng</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {logs && logs.length > 0 ? (
            logs.map((log) => (
              <TableRow key={log.id}>
                <TableCell className="font-medium">#{log.id.toString().slice(-6)}</TableCell>
                <TableCell>{log.fromSupplierName}</TableCell>
                <TableCell>{log.toWarehouseName}</TableCell>
                <TableCell className="text-right">{formatCurrency(log.totalAmount)}</TableCell>
                <TableCell className="text-center">
                  <Badge variant={log.status ? 'default' : 'destructive'}>
                    {log.status ? 'Hoàn thành' : 'Chờ xử lý'}
                  </Badge>
                </TableCell>
                <TableCell>{formatDate(log.createAt)}</TableCell>
                <TableCell className="text-right">
                  <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                      <Button variant="ghost" className="h-8 w-8 p-0">
                        <span className="sr-only">Mở menu</span>
                        <MoreHorizontal className="h-4 w-4" />
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent align="end">
                      <DropdownMenuLabel>Chức năng</DropdownMenuLabel>
                      <DropdownMenuItem onClick={() => onEdit(log)}>Sửa</DropdownMenuItem>
                      <DropdownMenuItem onClick={() => onDelete(log.id)}>Xóa</DropdownMenuItem>
                    </DropdownMenuContent>
                  </DropdownMenu>
                </TableCell>
              </TableRow>
            ))
          ) : (
            <TableRow>
              <TableCell colSpan={7} className="h-24 text-center">
                Chưa có đơn nhập hàng nào.
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  );
};