import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);
const Empty = () => import("../components/Empty.vue")
const DetailProduct = () => import("../views/DetailProduct.vue");
const Mypage = () => import("../views/Mypage.vue");
const Main = () => import("../views/Main.vue");
const Cart = () => import("../views/mycart.vue");
const Login = () => import("../views/login.vue");
const SignUp = () => import("../views/signUp.vue");
const EditUser = () => import("../views/editUser.vue");
const AdminPage = () => import("../views/admin/AdminPageHome.vue");
const AdminProductDetail = () => import("../views/admin/product/AdminProductDetail.vue");
const AdminProductUpdate= ()=> import("../views/admin/product/AdminProductUpdate.vue");
const routes = [
  {
    path: "/",
    name: "Main",
    component: Main
  },
  {
    path: "/mypage",
    name: "Mypage",
    component: Mypage
  },
  {
    path: "/cart",
    name: "Cart",
    component: Cart
  },
  {
    path: "/deatil",
    name: "DetailProduct",
    component: DetailProduct
  },
  {
    path: "/login",
    name: "Login",
    component: Login
  },
  {
    path: "/singUp",
    name: "SignUp",
    component: SignUp
  },
  {
    path: "/editUser",
    name: "EditUser",
    component: EditUser
  },
  {
    path: "/admin",
    component: Empty,
    children: [
      {
        path: "main",
        name: "AdminPage",
        component: AdminPage,

      },
      {
        path: "detail/:id",
        name: "AdminProductDetail",
        component: AdminProductDetail,
        props: true
      },
      {
        path: ":id/update",
        name: "AdminProductUpdate",
        component: AdminProductUpdate,
        props: true
      }


    ]
  },

];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
