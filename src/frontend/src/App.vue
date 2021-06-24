<template>
  <v-app>
    <div>
      <v-app-bar color="black accent-4" dense dark height="80">
        <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>
        <v-toolbar-title>Luxury Shop</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn icon :to="{ name: 'Search' }">
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
        <v-btn icon :to="{ name: 'Cart' }">
          <v-icon>mdi-cart</v-icon>
        </v-btn>

        <v-menu left bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-btn icon v-bind="attrs" v-on="on">
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </template>

          <v-list>
            <v-list-item v-if="!isLogin" :to="{ name: 'Login' }">
              <v-list-item-title>Login</v-list-item-title>
            </v-list-item>
            <v-list-item v-if="isLogin" @click="logout" :to="{ name: 'Login' }">
              <v-list-item-title>Logout</v-list-item-title>
            </v-list-item>
            <v-list-item v-if="isLogin" to="/editUser">
              <v-list-item-title>회원정보 수정</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-app-bar>
      <v-navigation-drawer v-model="drawer" absolute temporary>
        <v-list-item>
          <v-list-item-avatar>
            <v-img src="https://randomuser.me/api/portraits/men/78.jpg"></v-img>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title>{{
              this.$store.state.users.nickname
            }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-divider></v-divider>
        <v-list dense>
          <v-list-item :to="{ name: 'Main' }">
            <v-list-item-icon>
              <v-icon>mdi-view-dashboard</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>Home</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item :to="{ name: 'Mypage' }">
            <v-list-item-icon>
              <v-icon>mdi-forum</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>Mypage</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item :to="{ name: 'AdminPage' }">
            <v-list-item-icon>
              <v-icon>mdi-forum</v-icon>
            </v-list-item-icon>

            <v-list-item-content>
              <v-list-item-title>admin 임시</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-navigation-drawer>
    </div>

    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { getSearchTitle } from "@/api/search";
import HelloWorld from "./components/HelloWorld";
import {mapActions} from "vuex";

export default {
  name: "App",

  components: {
    HelloWorld
  },
  mounted() {},
  data: () => ({
    searchModel: null,
    drawer: false,
    group: null,
    is: false,
    nickname: "로그인하세요.",
    items: [
      { title: "Home", icon: "mdi-view-dashboard" },
      { title: "Mypage", icon: "mdi-forum" }
    ]
  }),
  methods: {
    ...mapActions({logout: 'users/logout'}),
    async logoutUser() {
      if (await this.logout()) {
        await this.$router.push({name: "Main"})
      }
    }
  },
  computed: {
    isLogin() {
      return (
        this.$store.state.users.jwt != undefined ||
        this.$store.state.users.jwt == ""
      );
    }
  }
};
</script>
<style>
th {
  font-size: 1.2rem !important;
  font-weight: 100;
}

td {
  font-size: 1rem !important;
}
</style>
