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
            <input type="file" @change="handleFileChange"/>
        </label>
    </v-btn>
</template>

<script>
export default {
  props: {
    // Using value here allows us to be v-model compatible.
    value: File,
  },
  methods: {
    handleFileChange(e) {
      // Whenever the file changes, emit the 'input' event with the file data.
      this.$emit('input', e.target.files[0]);
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