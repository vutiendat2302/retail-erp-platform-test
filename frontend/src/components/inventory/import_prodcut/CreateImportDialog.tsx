// file: components/inventory/CreateImportDialog.tsx
import React from 'react';
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { ImportForm } from './ImportForm';

interface CreateImportDialogProps {
  initialData: any;
  isOpen: boolean;
  onOpenChange: (isOpen: boolean) => void;
  onSuccess: () => void;
}

export const CreateImportDialog: React.FC<CreateImportDialogProps> = ({ initialData, isOpen, onOpenChange, onSuccess }) => {
  return (
    <Dialog open={isOpen} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-4xl">
        <DialogHeader>
          <DialogTitle>{initialData ? 'Sửa đơn nhập hàng' : 'Tạo đơn nhập hàng mới'}</DialogTitle>
          <DialogDescription>
            {initialData ? 'Cập nhật thông tin đơn hàng' : 'Điền thông tin cho đơn hàng mới'}
          </DialogDescription>
        </DialogHeader>
        <ImportForm 
          initialData={initialData}
          onSubmit={onSuccess} // Gọi onSuccess để đóng modal và tải lại bảng
          onClose={() => onOpenChange(false)}
        />
      </DialogContent>
    </Dialog>
  );
};