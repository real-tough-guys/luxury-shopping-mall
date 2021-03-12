<template>
  <v-container>
    <v-card>
      <h1 align="center">
        <v-icon size="xxx-large" color="black">mdi-gift</v-icon>
        My Cart
      </h1>
      <v-card v-for="product in products" :key="product.id">
        <v-layout>
          <v-flex xs3>
            <v-img :src="product.imgsrc" contain height="125px"></v-img>
          </v-flex>
          <v-layout column>
            <v-card-title
            ><h4>{{ product.title }}</h4></v-card-title
            >
            <v-card-text>{{ `가격 : ${product.price} 원 ` }}</v-card-text>
          </v-layout>
          <v-card-actions>
            <v-btn
                right
                color="blue-grey"
                class="ma-2 white--text"
                fab
                right
                @click="cartDelete(product.id)"
            >
              <v-icon dark>
                mdi-delete
              </v-icon>
            </v-btn>
          </v-card-actions
          >
        </v-layout>
      </v-card>
      <v-card-subtitle>
        <h3 align="center">
          Count

          <p style="color: orange">{{ products.length }}</p>
          Total Price($ {{ total }} 원)
        </h3>
      </v-card-subtitle
      >
    </v-card>
    <v-spacer/>
  </v-container>
</template>

<script>


export default {
  data() {
    return {
      products: [
        {
          id: 0,
          title: "미니멀 자켓",
          price: 70900,
          imgsrc:
              "https://image.msscdn.net/images/goods_img/20191114/1225639/1225639_1_125.jpg"
        },
        {
          id: 1,
          title: "스테디 오버 MA-1 자켓 그레이 SJOT1303",
          price: 58900,
          imgsrc:
              "https://image.msscdn.net/images/goods_img/20210311/1838974/1838974_2_125.jpg"
        },
        {
          id: 2,
          title: "카고 포켓 크롭 블루종 자켓",
          price: 54000,
          imgsrc:
              "https://image.msscdn.net/images/goods_img/20210222/1807421/1807421_2_125.jpg"
        }
      ],

    };
  },
  methods: {
    cartDelete(idx) {

      this.products.splice(idx - 1, 1);
    }
  },
  computed: {
    total() {
      var i = 0;
      return this.products.reduce((total) => {
        return Number(total) + Number(this.products[i].price);
        i++;
      }, 0);
    }
  }
};
</script>
