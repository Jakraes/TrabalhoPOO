# Instruções do Jogo

### Controlos:

 - **WASD** para mover o heroi
 - **1234** para selecionar slots do inventário
 - **Q** para apanhar items
 - **E** para largar items
 - **SPACE** para usar items
 <br>

### Criação de mapas:
>Todos os objetos do mapa são criados apenas com uma posição exceto os definidos a seguir *(excluindo aqueles que o jogador criar)*
 - **Porta**
	 * **Point2D** `posição no mapa`
	 * **int** `sala destino`
	 * **Point2D** `posição destino`
	 * *Opcional* **int** `número da chave que abre a porta`
	 * >Door,«posX»,«posY»,«destRoom»,«destPosX»,«destPosY»,«keyNumber»
	 
- **Chave**
	* **Point2D** `posição no mapa`
	* **int** `número da chave`
	* >Key,«posX»,«posY»,«keyNumber»
