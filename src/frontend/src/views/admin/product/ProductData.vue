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
      <v-data-table
          class="elevation-1"
          v-if="ProductList"
          v-model="selected"
          show-select
          :headers="headers"
          :items="ProductList"
          :single-select="singleSelect"
          :search="search"
          hide-default-footer
          @click:row="rowClick"
      >
<!--        <template slot="items" slot-scope="props">-->
<!--          <tr>-->
<!--            <td>{{ props.item.id }}</td>-->
<!--            <td>{{ props.item.name }}</td>-->
<!--            <td>{{ props.item.price }}원</td>-->
<!--          </tr>-->
<!--        </template>-->
        <template v-slot:top>
          <v-switch
              v-model="singleSelect"
              label="Single select"
              class="pa-3"
          ></v-switch>
        </template>
      </v-data-table>

      <v-btn small @click="deleteItem" class="mx-2" fab dark color="indigo">
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
          <v-card-title class="headline grey lighten-2">
          상품 등록
          </v-card-title>

          <v-card-text>
            <ProductCreate />
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
import ProductCreate from "../product/ProductCreate.vue";
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
        { text: "no", value: "id", sortable: true },
        { text: "상품이름", value: "name", sortable: true },
        { text: "가격", value: "price", sortable: true },
        { text: "색상", value: "color", sortable: true },
        { text: "사이즈", value: "size", sortable: true },
        { text: "수량", value: "quantity", sortable: true },
        { text: "카테고리", value: "category", sortable: true }
      ],
      ProductList: [
        {
          id: 1,
          name: "블루종",
          price: "80000",
          color: ["Navy","white"],
          size:["M","L","XL"],
          quantity:20,
          category:"Outer"
        },
        {
          id: 2,
          name: "셔츠",
          price: "900000",
          color: ["black","Navy"],
          size:["free"],
          quantity:20,
          category:"Top"
        },

        {
          id: 3,
          name: "블랙진",
          price: "900000",
          color: ["black","white"],
          size:["M","L","XL"],
          quantity:20,
          category:"Bottom"
        }
      ]
    };
  },
  methods: {
    deleteItem() {
      console.log(this.selected);
      for (var i = 0; i < this.selected.length; i++) {
        var selectId = this.selected[i].id;
        this.ProductList.splice(selectId - 1, 1);
      }
    },
    rowClick(id) {
      console.log("들어오니?")
      id = id.id
      this.$router.push({ name: 'AdminProductDetail', params: { id: id } })
    },
  }
};
</script>
