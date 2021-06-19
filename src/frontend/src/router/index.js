import Vue from "vue";
import VueRouter from "vue-router";
import store from "../store/modules/users";

Vue.use(VueRouter);
const Empty = () => import("../components/Empty.vue")
const DetailProduct = () => import("../views/DetailProduct.vue");
const Mypage = () => import("../views/Mypage.vue");
const Main = () => import("../views/Main.vue");
const Cart = () => import("../views/mycart.vue");
const Search = () => import("../views/Search.vue");
const Login = () => import("../views/login.vue");
const SignUp = () => import("../views/signUp.vue");
const EditUser = () => import("../views/editUser.vue");
const PaymentCheck = () => import("../views/PaymentCheck.vue");
const AdminPage = () => import("../views/admin/AdminPageHome.vue");
const AdminProductDetail = () => import("../views/admin/product/AdminProductDetail.vue");
const AdminProductUpdate = () => import("../views/admin/product/AdminProductUpdate.vue");
const Payment=()=> import("../views/Payment.vue")
const routes = [
    {
        path: "/",
        name: "Main",
        component: Main
    },
    {
        path: "/mypage",
        name: "Mypage",
        component: Mypage,
        meta: {authRequired: true}
    },
    {
        path: "/cart",
        name: "Cart",
        component: Cart,
        meta: {authRequired: true}
    },
    {
        path: "/detail/:id",
        name: "DetailProduct",
        component: DetailProduct,
        props: true
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
        component: EditUser,
        meta: {authRequired: true}
    },
    {
        path: "/search",
        name: "Search",
        component: Search,
    },
    {
        path: "/payment",
        name: "Payment",
        component: Payment,
        props: true
    },
    {
        path: "/payment/check",
        name: "PaymentCheck",
        component: PaymentCheck,
        props: true
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
        ],
        meta: {authRequired: true}
    },
];
const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

router.beforeEach(function (to, from, next) {
    if (to.matched.some(routeInfo => routeInfo.meta.authRequired)) {
        if (store.state.jwt === null) {
            window.alert('로그인이 필요합니다.');
        } else {
            next();
        }
    } else {
        next();
    }
})

export default router;
