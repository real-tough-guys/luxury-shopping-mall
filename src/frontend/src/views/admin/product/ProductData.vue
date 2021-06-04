<template>
  <div>

    <v-card>
      <v-card-title>
        Product Table
        <v-spacer></v-spacer>
        <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
        ></v-text-field>
      </v-card-title>
      <v-data-table class="elevation-1" v-if="productList" v-model="selected" show-select :headers="headers"
                    :items="productList.data" :single-select="singleSelect" @click:row="rowClick" :search="search"
                    hide-default-footer>
        <template slot="items" slot-scope="props">

        </template>
        <template v-slot:top>
          <v-switch v-model="singleSelect" label="Single select" class="pa-3"></v-switch>
        </template>
      </v-data-table>
      <v-btn small @click="" class="mx-2" fab dark color="indigo">
        <v-icon dark>
          mdi-minus
        </v-icon>
      </v-btn>

      <v-dialog v-model="ProductCreateDialog" width="500">
        <template v-slot:activator="{ on, attrs }">
          <v-btn
              small
              v-bind="attrs"
              v-on="on"
              class="mx-2"
              fab
              dark
              color="indigo"
          >
            <v-icon dark>
              mdi-plus
            </v-icon>
          </v-btn>
        </template>

        <v-card>
          <v-card-title class="headline  primary" style="color: white">
            상품 등록
          </v-card-title>
          <v-card-text>
            <ProductCreate/>
          </v-card-text>
          <v-divider></v-divider>
          <v-card-actions>
            <v-spacer></v-spacer>

            <v-btn color="error" text @click="ProductCreateDialog = false">
              Cancel
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-card>
  </div>
</template>
<script>
import {mapActions, mapState} from "vuex";
import ProductCreate from "../product/ProductCreate.vue";
import axios from "axios";

export default {
  components: {
    ProductCreate
  },
  data() {
    return {
      ProductCreateDialog: false,
      selected: [],
      singleSelect: false,
      search: "",
      headers: [
        {text: "no", value: "id", sortable: true},
        {text: "상품이름", value: "productName", sortable: true},
        {text: "가격", value: "productPrice", sortable: true},
        {text: "카테고리", value: "productCategory", sortable: true},
        {text: "사이즈", value: "productSize", sortable: true},
        {text: "색상", value: "productColor", sortable: true},
        {text: "수량", value: "quantity", sortable: true},
      ]
    };
  },
  mounted() {
    this.init();
  },
  computed: {
    ...mapState({
      productList: state => state.products.productList
    })
  },
  methods: {
    init() {
      this.getProductList();
    },
    deleteItem() {
      for (var i = 0; i < this.selected.length; i++) {
        var selectId = this.selected[i].id;
        axios
            .delete("/api/products/" + selectId)
            .then(res => {
              console.log(res);
            })
            .catch(err => {
              console.log(err);
            });
      }
    },
    rowClick(id) {
      id = id.id;
      this.$router.push({name: "AdminProductDetail", params: {id: id}});
    },
    ...mapActions({
      getProductList: "products/getProductsList"
    })
  }
};
</script>
