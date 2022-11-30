@ECHO off
@CHCP 65001 >NUL

ECHO ╔════════════════════════════════════════════════════════════╗
ECHO ║ Este arquivo em lotes copiará o arquivo ABNT_AuthorEDT.XSL ║
ECHO ║ contido neste diretório para o local apropriado no seu     ║
ECHO ║ computador.                                                ║
ECHO ║                                                            ║
ECHO ║ Desenvolvido por Prof. Dr. David Buzatto                   ║
ECHO ╚════════════════════════════════════════════════════════════╝
ECHO:

ECHO [91mCaso o MS Word esteja aberto, feche-o.[0m
ECHO Pressione qualquer tecla para continuar.
PAUSE >NUL
ECHO:

ECHO Copiando...
xcopy /Y ABNT_AuthorEDT.XSL %AppData%\Microsoft\Bibliography\Style
ECHO Cópia realizada!
ECHO:

ECHO [32mTudo pronto ;) [94mAgora abra o MS Word e verifique se o
ECHO estilo bibliográfico está disponível![0m
ECHO:

ECHO Pressione qualquer tecla para finalizar.
PAUSE >NUL