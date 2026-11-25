# Print Fácil — versão web/PWA + base Android

## O que está implementado
- Interface responsiva em HTML5, CSS3 e JavaScript puro.
- Captura real via `navigator.mediaDevices.getDisplayMedia()` (o navegador sempre exige escolha/autorização explícita).
- Prévia, salvar, descartar, compartilhar/download.
- Galeria local com IndexedDB, seleção e exclusão múltipla.
- Botão flutuante arrastável dentro da página, com posição lembrada.
- Ajustes de tamanho, transparência, formato, qualidade, vibração e tema.
- Service Worker para abrir a aplicação e consultar capturas já salvas offline.

## Executar
A API de captura normalmente exige contexto seguro (`https://` ou `http://localhost`). Não abra o arquivo com `file://`.
1. Descompacte o projeto.
2. Na pasta, execute `python -m http.server 8080`.
3. Abra `http://localhost:8080` no Chrome ou Edge.
4. Clique em **Iniciar captura** e escolha a aba, janela ou tela no seletor do navegador.

## Limitações importantes
A API Web não permite captura silenciosa, sem consentimento, nem captura garantida com um único toque após autorização anterior. O navegador pode solicitar a seleção da fonte em cada sessão. O botão flutuante web existe somente sobre a página Print Fácil; não aparece sobre outros aplicativos.

## Base Android
A pasta `android/` contém um esqueleto Kotlin para a evolução nativa. A implementação real de overlay e MediaProjection requer projeto Android Studio, assinatura, permissões, serviço em primeiro plano, notificação persistente e consentimento MediaProjection conforme a versão do Android. O esqueleto não deve ser considerado um APK pronto.
