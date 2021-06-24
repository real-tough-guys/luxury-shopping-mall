<template>
  <div>
    <v-card class="mx-auto overflow-hidden" height="auto">
      <v-container fluid>
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
      <infinite-loading
          @infinite="infiniteHandler"
          spinner="waveDots"
          forceUseInfiniteWrapper
      ></infinite-loading>
    </v-card>

  </div>
</template>

<script>
import InfiniteLoading from 'vue-infinite-loading';
import axios from 'axios';
import myMixin from "@/filter";

export default {
  mixins: [myMixin],
  data() {
    return {
      limit: 1,
      listData: [],
    };
  },
  components: {
    InfiniteLoading,
  },
  methods: {
    infiniteHandler($state) {
      axios.get("/api/products/main/", {params: {limit: this.limit}}
      ).then((res) => {
        setTimeout(() => {
          if (res.data.length) {
            this.listData = this.listData.concat(res.data);
            $state.loaded();
            this.limit += 1;
          } else {
            $state.complete();
          }
        }, 1000);
      }).catch((err) => {
        console.log(err)
      });
    },
    detailPush(id) {
      this.$router.push({name: 'DetailProduct', params: {id: id}});
    },
  }
}
</script>
<style>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: 0.5;
  position: absolute;
  width: 100%;
}
</style>