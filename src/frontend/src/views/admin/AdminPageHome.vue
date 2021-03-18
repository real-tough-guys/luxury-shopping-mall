<template>
  <div>
    <v-card>
      <v-toolbar color="primary" dark flat prominent>
        <h1>Admin</h1>

        <template v-slot:extension>
          <v-tabs v-model="tabs" centered>
            <v-tab>User</v-tab>
            <v-tab>Product</v-tab>
            <v-tab>Order</v-tab>
          </v-tabs>
        </template>
      </v-toolbar>

      <v-tabs-items v-model="tabs">
        <v-tab-item>
          <v-card flat>
            <v-card-text>
              <UserData />
            </v-card-text>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card flat>
            <ProductData />
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card flat>
            <v-card-title class="headline">
              2차 구현 기능
            </v-card-title>

          </v-card>
        </v-tab-item>
      </v-tabs-items>
    </v-card>
  </div>
</template>
<script>
import UserData from "../admin/user/UserData.vue";
import ProductData from "../admin/product/ProductData.vue";
export default {
  components: {
    UserData,
    ProductData
  },
  data() {
    return {
      tabs: null
    };
  }
};
</script>
