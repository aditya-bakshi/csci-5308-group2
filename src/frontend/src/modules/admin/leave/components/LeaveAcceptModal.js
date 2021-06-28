//! Ant Imports

import { Modal } from "antd";

function LeaveAcceptModal({ record, modalState, onCreate, onCancel }) {
  const { visible, confirmLoading } = modalState;

  return (
    <Modal
      visible={visible}
      title={`Approve staff request: ${record.firstName} ${record.lastName}`}
      okText="Approve"
      cancelText="Cancel"
      onCancel={onCancel}
      onOk={onCreate}
      centered
      keyboard={false}
      maskClosable={false}
      confirmLoading={confirmLoading}
    >
      Are you sure, you want to approve this request?
    </Modal>
  );
}

export default LeaveAcceptModal;
