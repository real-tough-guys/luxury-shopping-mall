<template>
  <div v-else>
    <v-card class="mx-auto" height="auto" align="center"></v-card>

    <v-card class="mx-auto overflow-hidden" height="auto">
      <v-toolbar color="grey accent-4" dark>
        <v-text-field
          label="Luxury shop search"
          hide-details
          single-line
          v-model="productName"
          @keyup.enter="nameSearch"
        ></v-text-field>
        <v-btn icon @click="nameSearch">
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </v-toolbar>
      <v-tabs centered color="grey">
        <v-tab @click="categorySearch(items[0])">Outer</v-tab>
        <v-tab @click="categorySearch(items[1])">Top</v-tab>
        <v-tab @click="categorySearch(items[2])">Bottom</v-tab>
      </v-tabs>
      <br />
      <h2 style="text-align: center">
        "
        <span style="color: cornflowerblue ;font-weight: bold">{{
          searchKeyword
        }}</span>
        " 에 대한 검색 결과
        <span style="color: cornflowerblue ;font-weight: bold"
          >{{ listData.length }}
        </span>
        건
      </h2>
      <br />
      <v-container fluid>
        <loding v-if="isLoading" />
        <v-row>
          <v-col
            v-for="(item, i) in listData"
            :key="`item-${i}`"
            cols="12"
            sm="4"
          >
            <div>
              <v-hover v-slot="{ hover }">
                <v-card class="mx-auto" max-width="344">
                  <div @click="detailPush(item.id)">
                    <v-img
                      v-bind:src="
                        item.productImageurl[0] | loadImgOrPlaceholder
                      "
                      :aspect-ratio="11 / 13"
                      height="mx-auto"
                    >
                      <v-expand-transition>
                        <div
                          v-if="hover"
                          class="d-flex transition-fast-in-fast-out blue-grey darken-2 v-card--reveal display-3 white--text"
                          style="height: 100%;"
                        ></div>
                      </v-expand-transition>
                    </v-img>
                  </div>
                  <v-card-text class="pt-6" style="position: relative;">
                    <v-btn
                      absolute
                      color="black"
                      class="white--text"
                      fab
                      large
                      right
                      top
                    >
                      <v-icon>mdi-heart</v-icon>
                    </v-btn>

                    <div class="font-weight-bold black--text title mb-2">
                      {{ item.productName }}
                    </div>
                    <div class="font-weight-light grey--text title mb-2">
                      {{ item.productContent }}
                    </div>
                    <div class="font-weight-medium title mb-2">
                      {{ item.productPrice | moneyFilter }} won
                    </div>
                  </v-card-text>
                </v-card>
              </v-hover>
            </div>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </div>
</template>
<script>
import { getSearchTitle } from "@/api/search";
import myMixin from "@/filter";
import { mapState } from "vuex";

export default {
  mixins: [myMixin],
  data() {
    return {
      isLoading: false,
      listData: [],
      productName: "",
      title: "",
      items: ["outer", "top", "bottom"],
      searchKeyword: ""
    };
  },
  computed: {},

  methods: {
    nameSearch: function() {
      getSearchTitle(this.productName, "")
        .then(res => {
          this.listData = res.data;
          this.searchKeyword = this.productName;
          this.productName = "";
        })
        .catch(res => {
          alert(res.response.data);
        });
    },
    categorySearch: function(category) {
      getSearchTitle("", category)
        .then(res => {
          this.listData = res.data;
          this.searchKeyword = category;
        })
        .catch(res => {
          alert(res.response.data);
        });
    },
    detailPush(id) {
      this.$router.push({ name: "DetailProduct", params: { id: id } });
    }
  }
};
</script>
