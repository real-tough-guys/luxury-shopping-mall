import Vue from "vue";
import VueRouter from "vue-router";

Vue.use(VueRouter);
const Home = () => import(/* webpackChunkName: "jun" */ "../views/Home.vue");
const Mypage = () =>
  import(/* webpackChunkName: "jun" */ "../views/Mypage.vue");
const Product = () =>
  import(/* webpackChunkName: "jun" */ "../views/product.vue");
const Cart = () => import(/* webpackChunkName: "jun" */ "../views/mycart.vue");
const Signup = () =>
  import(/* webpackChunkName: "jun" */ "../views/signUp.vue");

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
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
    path: "/product",
    name: "Product",
    component: Product
  },
  {
    path: "/signup",
    name: "Signup",
    component: Signup
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
