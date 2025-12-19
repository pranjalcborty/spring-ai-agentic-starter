#!/bin/bash

/bin/ollama serve &
pid=$!

sleep 5

models=("qwen3:4b" "mxbai-embed-large")
for model in "${models[@]}"
do
    echo "🔴 $model Loading..."
    ollama pull $model
    echo "🟢 $model Loaded"
done

wait $pid
