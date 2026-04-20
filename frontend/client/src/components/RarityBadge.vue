<template>
  <span class="rarity-badge" :style="badgeStyle">
    {{ rarityInfo.name }}
  </span>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { getRarityInfo, type RarityLevel } from '@/utils/images'

const props = defineProps<{
  level: RarityLevel | number
}>()

const rarityInfo = computed(() => getRarityInfo(props.level as RarityLevel))

const badgeStyle = computed(() => ({
  backgroundColor: rarityInfo.value.bgColor,
  color: '#fff',
  borderColor: rarityInfo.value.color
}))
</script>

<style scoped>
.rarity-badge {
  display: inline-block;
  padding: 2px 8px;
  font-size: 11px;
  font-weight: 600;
  border-radius: 4px;
  border: 1px solid;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}
</style>
