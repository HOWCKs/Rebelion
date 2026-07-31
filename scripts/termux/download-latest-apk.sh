#!/data/data/com.termux/files/usr/bin/bash
set -euo pipefail

REPO="${REBELION_REPO:-HOWCKs/Rebelion}"
BRANCH="${REBELION_BRANCH:-arena/019fb964-rebelion}"
WORKFLOW="${REBELION_WORKFLOW:-Android Prototype APK}"
ARTIFACT="${REBELION_ARTIFACT:-rebelion-prototype-debug-apk}"
OPEN_AFTER_DOWNLOAD="false"

if [[ "${1:-}" == "--open" ]]; then
  OPEN_AFTER_DOWNLOAD="true"
fi

if ! command -v gh >/dev/null 2>&1; then
  echo "Erro: GitHub CLI não encontrado. Instale com: pkg install gh"
  exit 1
fi

if ! gh auth status >/dev/null 2>&1; then
  echo "Erro: gh não está autenticado. Rode: gh auth login -h github.com -p https -s repo,workflow"
  exit 1
fi

if [[ -d "$HOME/storage/downloads" ]]; then
  DOWNLOAD_ROOT="$HOME/storage/downloads"
elif [[ -d "/sdcard/Download" && -w "/sdcard/Download" ]]; then
  DOWNLOAD_ROOT="/sdcard/Download"
else
  echo "Não encontrei acesso à pasta Downloads do Android."
  echo "No Termux, rode primeiro: termux-setup-storage"
  echo "Depois autorize o acesso aos arquivos e execute este script novamente."
  exit 1
fi

DEST_DIR="$DOWNLOAD_ROOT/Rebelion"
TMP_DIR="$(mktemp -d)"
trap 'rm -rf "$TMP_DIR"' EXIT

mkdir -p "$DEST_DIR"

echo "Buscando último build bem-sucedido..."
echo "Repo: $REPO"
echo "Branch: $BRANCH"
echo "Workflow: $WORKFLOW"

RUN_ID="$(gh run list \
  --repo "$REPO" \
  --branch "$BRANCH" \
  --workflow "$WORKFLOW" \
  --status success \
  --limit 1 \
  --json databaseId \
  --jq '.[0].databaseId // empty')"

if [[ -z "$RUN_ID" ]]; then
  echo "Erro: nenhum build bem-sucedido encontrado para baixar."
  echo "Confira com: gh run list --repo $REPO --branch $BRANCH"
  exit 1
fi

echo "Baixando artifact '$ARTIFACT' do run $RUN_ID..."
gh run download "$RUN_ID" \
  --repo "$REPO" \
  -n "$ARTIFACT" \
  -D "$TMP_DIR"

APK_PATH="$(find "$TMP_DIR" -type f -name '*.apk' | head -n 1)"

if [[ -z "$APK_PATH" ]]; then
  echo "Erro: download concluído, mas nenhum APK foi encontrado no artifact."
  exit 1
fi

TIMESTAMP="$(date +%Y%m%d-%H%M%S)"
LATEST_APK="$DEST_DIR/Rebelion-prototype-latest.apk"
VERSIONED_APK="$DEST_DIR/Rebelion-prototype-$TIMESTAMP.apk"

cp "$APK_PATH" "$LATEST_APK"
cp "$APK_PATH" "$VERSIONED_APK"

chmod 644 "$LATEST_APK" "$VERSIONED_APK" || true

echo "APK salvo em:"
echo "- $LATEST_APK"
echo "- $VERSIONED_APK"

if [[ "$OPEN_AFTER_DOWNLOAD" == "true" ]]; then
  if command -v termux-open >/dev/null 2>&1; then
    echo "Abrindo instalador do APK..."
    termux-open "$LATEST_APK"
  else
    echo "termux-open não encontrado. Abra manualmente o APK na pasta Downloads/Rebelion."
  fi
else
  echo "Para abrir e instalar agora, rode:"
  echo "termux-open '$LATEST_APK'"
fi
