
import Vue from "vue";
import VueRouter from "vue-router";



Vue.use(VueRouter)
const DetailProduct = () => import("../views/DetailProduct.vue");
const Mypage = () => import("../views/Mypage.vue");
const Main = () => import("../views/Main.vue");
const Cart = () => import("../views/mycart.vue");


const routes = [
  {
    path: "/",
    name: "Main",
    component: Main
  },
  {
    path: '/mypage',
    name: 'Mypage',
    component: Mypage,
  },
  {
    path: '/cart',
    name: 'Cart',
    component: Cart,
  },
  {
    path: '/deatil',
    name: 'DetailProduct',
    component: DetailProduct,

  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
