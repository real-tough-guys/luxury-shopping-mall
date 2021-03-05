<template>
  <v-app>
    <div>


      <v-app-bar
          color="black accent-4"
          dense
          dark
          height="80"
      >
        <v-app-bar-nav-icon @click="drawer = true"></v-app-bar-nav-icon>

        <v-toolbar-title>Luxury Shop</v-toolbar-title>

        <v-spacer></v-spacer>
        <v-col
            cols="6"
            md="3"
        >
          <v-form ref="form">
            <v-text-field
                v-model="model"
                :counter="max"
                :rules="rules"
                label="Luxury shop search"
            ></v-text-field>
          </v-form>
        </v-col>


        <v-btn icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
        <v-btn icon>
          <v-icon>mdi-cart</v-icon>
        </v-btn>

        <v-menu
            left
            bottom
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn
                icon
                v-bind="attrs"
                v-on="on"
            >
              <v-icon>mdi-dots-vertical</v-icon>
            </v-btn>
          </template>

          <v-list>
            <v-list-item
                v-for="n in 5"
                :key="n"
                @click="() => {}"
            >
              <v-list-item-title>Option {{ n }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-app-bar>
        <v-navigation-drawer
            v-model="drawer"
            absolute
            temporary
        >
          <v-list
              nav
              dense
          >
            <v-list-item-group
                v-model="group"
                active-class="deep-purple--text text--accent-4"
            >
              <v-list-item>
                <v-list-item-icon>
                  <v-icon>mdi-home</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Home</v-list-item-title>
              </v-list-item>

              <v-list-item>
                <v-list-item-icon>
                  <v-icon>mdi-account</v-icon>
                </v-list-item-icon>
                <v-list-item-title>Account</v-list-item-title>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-navigation-drawer>


    </div>
    <v-spacer></v-spacer>
    <v-main>
      <router-view></router-view>
      <!--      <HelloWorld/>-->
    </v-main>
  </v-app>
</template>

<script>
import HelloWorld from './components/HelloWorld';
import request from "request";

export default {
  name: 'App',

  components: {
    HelloWorld,
  },
  mounted() {
    request("http://localhost:8090/api/hello", function (error, response, body) {
      window.console.log('error', error);
      window.console.log('statusCode', response && response.statusCode);
      window.console.log('body', body);

    });
  },

  data: () => ({
    drawer: false,
    group: null,
  }),
};
</script>
