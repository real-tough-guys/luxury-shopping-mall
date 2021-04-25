<template>
  <div>
    <v-card>
      <v-toolbar color="primary" dark flat prominent>
        <h1>Admin Detail</h1>
      </v-toolbar>

      <v-card max-width="450" max-height="auto" class="mx-auto my-12">
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-card-title>
            <v-text-field
              color="primary"
              :rules="titleRules"
              required
              v-model="title"
              counter="15"
              label="name"
              clearable
              clear-icon="mdi-close-circle"
              outlined
            ></v-text-field>
          </v-card-title>

          <v-card-title>
            <v-textarea
              color="primary"
              :rules="contentRules"
              v-model="content"
              counter="50"
              label="Content"
              clearable
              clear-icon="mdi-close-circle"
              outlined
            ></v-textarea>
          </v-card-title>

          <v-card-title>
            <v-text-field
              color="primary"
              v-model="price"
              label="Price"
              clearable
              clear-icon="mdi-close-circle"
              outlined
            ></v-text-field
            >원
          </v-card-title>

          <v-card-title>
            <v-select
              color="primary"
              :items="items"
              :rules="[v => !!v || 'category를 선택해주세요']"
              required
              v-model="category"
              :menu-props="{ top: true, offsetY: true }"
              label="Category"
              outlined
            ></v-select>
          </v-card-title>

          <v-card-subtitle>
            <h4>Image는 3장만 올려주세요</h4>
          </v-card-subtitle>
          <input
            type="file"
            ref="imageInput"
            name="images[]"
            id="photo"
            @change="imagesAdd"
            hidden
            multiple
          />

          <v-btn
            color="blue-grey"
            class="ma-2 white--text"
            @click="onClickImageUpload"
          >
            Imagae Upload

            <v-icon right dark>
              mdi-cloud-upload
            </v-icon>
          </v-btn>
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">
                    Image
                  </th>
                  <th class="text-left">
                    Delete
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(img, i) in image" v-bind:key="i">
                  <th>
                    <v-img :src="img" width="130" height="130"></v-img>
                  </th>
                  <th>
                    <v-btn
                      x-small
                      dark
                      color="pink"
                      v-show="image"
                      @click="removeImage(i)"
                    >
                      <v-icon dark>
                        mdi-delete
                      </v-icon>
                      Image {{ i + 1 }}
                    </v-btn>
                  </th>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
          <v-card-subtitle>
            <v-checkbox
              v-model="checkbox"
              :rules="[v => !!v || '상품 거래에 동의 해주세요']"
              label="Do you agree?"
              required
            ></v-checkbox>
          </v-card-subtitle>
        </v-form>
        <v-btn
          color="blue-grey"
          block
          class=" white--text"
          :disabled="!valid"
          @click="validate"
        >
          Submit
          <v-icon right color="white">
            mdi-checkbox-marked-circle
          </v-icon>
        </v-btn>
      </v-card>
    </v-card>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data() {
    return {
      valid: true,
      titleRules: [
        v => !!v || "title is required",
        v => (v && v.length <= 15) || "Name must be less than 15 characters"
      ],
      contentRules: [
        v => !!v || "content is required",
        v => (v && v.length <= 50) || "50자 이하로 작성 부탁해용"
      ],
      checkbox: false,
      items: ["Top", "Bottom", "Outer"],
      images: {},
      image: [],
      photoFile: [],
      title: "미니멀자켓",
      price: "90000",
      category: "Outer",
      content: "이 상품 진짜 강추!!"
    };
  },
  methods: {
    validate() {
      this.$refs.form.validate();
      this.allSubmit();
    },

    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    imagesAdd(e) {
      var files = e.target.files || e.dataTransfer.files;

      this.images = [];
      this.image = [];
      Array.prototype.push.apply(this.images, files); //array element add
      if (!this.images.length) return;
      if (this.images.length > 3) {
        return alert("이미지는 3장만 가능해용");
      }
      this.createImage(this.images);
    },

    createImage(file) {
      for (var i = 0; i < file.length; i++) {
        var reader = new FileReader();
        var vm = this;

        reader.onload = e => {
          vm.image.push(e.target.result);
        };
        reader.readAsDataURL(file[i]);
      }
    },

    removeImage(key) {
      this.image.splice(key, 1);
      this.images.splice(key, 1);
    },
    allSubmit() {
      var frm = new FormData();
      // this.photoFile = this.images;
      for (var i = 0; i < this.images.length; i++) {
        frm.append("files", this.images[i]);
      }

      frm.append("title", this.title);
      frm.append("content", this.content);
      frm.append("price", this.price);
      frm.append("category", this.category);

      axios
        .post("http://localhost:8090/api/image", frm, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>
