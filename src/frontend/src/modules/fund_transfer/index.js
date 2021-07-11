import { useEffect, useState } from "react";

//! Ant Imports

import { Form, Input, Button, Typography } from "antd";

//! User Files

import { toast } from "common/utils";
import { REGEX } from "common/constants";
import api from "common/api";
import Loading from "components/Loading";
import TextArea from "antd/lib/input/TextArea";
import ServerError from "components/ServerError";

const { Title } = Typography;

function FundTransfer() {
  const [loading, setLoading] = useState(false);
  const [formLoading, setFormLoading] = useState(false);
  const [err, setErr] = useState(false);
  const [accountData, setAccountData] = useState({});

  const [form] = Form.useForm();

  const senderAccountNumber = accountData?.accountNumber;

  const onFinish = async (values) => {
    setLoading(true);
    try {
      const response = await api.post("/account/activity", values);
      const { data } = response;
      if (data?.success) {
        toast({
          message: data.message,
          type: "success",
        });
        form.resetFields();
      }
    } catch (err) {
      if (err.response?.data) {
        toast({
          message:
            err.response.data.message === "Bad credentials"
              ? "Please check your credentials"
              : err.response.data.message,
          type: "error",
        });
      } else {
        toast({
          message: "Something went wrong!",
          type: "error",
        });
      }
    }
    setLoading(false);
  };

  const fetchUserAccount = async () => {
    setLoading(true);
    setFormLoading(true);
    try {
      const response = await api.get(`/account/me`);
      const { data } = response;
      setAccountData(data);
    } catch (err) {
      setErr(true);
      toast({
        message: "Something went wrong!",
        type: "error",
      });
    } finally {
      setLoading(false);
      setFormLoading(false);
    }
  };

  useEffect(() => {
    form.resetFields();
    // eslint-disable-next-line
  }, [formLoading]);

  useEffect(() => {
    fetchUserAccount();
  }, []);

  if (formLoading) return <Loading />;
  if (err) return <ServerError />;
  return (
    <div className="login">
      <Title level={3} className="sdp-text-strong">
        Fund Transfer
      </Title>
      <Form
        form={form}
        name="normal_login"
        className="login-form form"
        onFinish={onFinish}
        initialValues={{
          senderAccountNumber,
        }}
      >
        <Form.Item
          name="senderAccountNumber"
          rules={[
            { required: true, message: "This field is required" },
            { pattern: REGEX.NUMBER, message: "Only digits are allowed" },
          ]}
        >
          <Input readOnly placeholder="Sender Account Number" />
        </Form.Item>
        <Form.Item
          name="receiverAccountNumber"
          rules={[
            { required: true, message: "This field is required" },
            { pattern: REGEX.NUMBER, message: "Only digits are allowed" },
          ]}
        >
          <Input placeholder="Receiver Account Number" />
        </Form.Item>
        <Form.Item
          name="transactionAmount"
          rules={[
            { required: true, message: "This field is required" },
            { pattern: REGEX.NUMBER, message: "Only digits are allowed" },
          ]}
        >
          <Input placeholder="Amount" />
        </Form.Item>
        <Form.Item name="reason">
          <TextArea rows={4} placeholder="Comment (Optional)" />
        </Form.Item>
        <Form.Item>
          <Button
            loading={loading}
            type="primary"
            htmlType="submit"
            className="login-form-button button"
          >
            Transfer Funds
          </Button>
        </Form.Item>
      </Form>
    </div>
  );
}

export default FundTransfer;