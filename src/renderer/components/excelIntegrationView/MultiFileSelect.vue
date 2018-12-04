<template>
  <!--
    Everything is wrapped in a label, which acts as a clickable wrapper around a form element.
    In this case, the file input.
  -->
    <v-btn style="padding:0px;">
        <label style="padding:10px;" class="file-select">
            <!-- We can't use a normal button element here, as it would become the target of the label. -->
            <div class="select-button">
            <!-- Display the filename if a file has been selected. -->
                <v-flex>파일선택</v-flex>
            </div>
            <!-- Now, the file input that we hide. -->
            <input type="file" @change="handleFileChange" multiple/>
        </label>
    </v-btn>
</template>

<script>
export default {
  props: {
    // Using value here allows us to be v-model compatible.
    value: FileList,
  },
  methods: {
    handleFileChange(e) {
      let tFiles = e.target.files;
      let fname = '';
      let ext = '';
      for (let ii = 0; ii < tFiles.length; ii += 1) {
        fname = tFiles[ii].name;
        ext = fname.slice(fname.lastIndexOf('.') + 1).toLowerCase();
        if (ext !== 'xlsx' && ext !== 'xls') {
          // alert('형식이 올바르지 않은 파일이 포함되어있습니다.');
          // this.$emit('input', null);
          // return;
          tFiles = tFiles.splice(ii, 1);
          ii -= 1;
        }
      }
      // Whenever the file changes, emit the 'input' event with the file data.
      this.$emit('input', tFiles);
    },
  },
};
</script>

<style scoped>
.file-select > .select-button {
  color: white;
}

/* Don't forget to hide the original file input! */
.file-select > input[type="file"] {
  display: none;
}
</style>