/* ROUTERS  */
export const ROUTES = {
  MAIN: "/",
  LOGIN: "/login",
  LOGOUT: "/logout",
  REGISTER: "/register",
  ADD_USER: "/bank/user/create",
  FORGET_PASSWORD: "/forget-password",
  RESET_PASSWORD: "/reset-password",
  CHANGE_PASSWORD: "/change-password",
  USERS_MANAGEMENT: "/users",
};

export const APP_NAME = "DAL BMS";

export const ACCOUNT_STATUS = {
  PENDING: "PENDING",
  ACTIVE: "ACTIVE",
  REJECTED: "REJECTED",
};

/*  Modules */
export const MODULES = {
  DASHBOARD: "Dashboard",
  USERS_MANAGEMENT: "User Management",
  ADD_USER: "Add User",
};

/* Authentication */
export const TOKEN = "TOKEN";
export const USER = "USER";
export const ADMIN = "ADMIN";
export const USER_ID = "USER_ID";
export const ROLE = "ROLE";

/* Errors */

export const SERVER_ERROR = "SERVER_ERROR";

export const ROLES = {
  ROLE_MANAGER: "ROLE_MANAGER",
  ROLE_HR: "ROLE_HR",
  ROLE_EMPLOYEE: "ROLE_EMPLOYEE",
  ROLE_USER: "ROLE_USER",
};

/* Date and time */
export const defaultDateFormat = "MM/DD/YYYY";

export const REGEX = {
  NAME: /^[a-z ,.'-]+$/i,
  ZIPCODE: /^[0-9]{5,6}$/,
  CITY: /^[a-zA-Z]+(?:[\s-][a-zA-Z]+)*$/,
  WEB_URL:
    /(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]+\.[^\s]{2,}|www\.[a-zA-Z0-9]+\.[^\s]{2,})/gi,
  PASSWORD: /^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9!@#$%^&*]{8,16}$/,
  PHONE: /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/,
  EMAIL: /^[a-z0-9.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
  AMOUNT: /^\d+$|^\d+\.\d*$/,
  OPTIONALNEGATIVEAMOUNT: /^[-]?\d+$|^[-]?\d+\.\d*$/,
  NUMBER: /^\d+$/,
};