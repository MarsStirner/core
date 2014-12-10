<?php

$core_host = 'localhost:8080';
$print_system_host = '192.168.1.121:5000';

function proxy_url ($url, $header_host=null) {
    set_time_limit(240);

    $ch = curl_init( $url );

    $cookie = array();
    foreach ( $_COOKIE as $key => $value ) {
        $cookie[] = $key . '=' . $value;
    }
    $cookie = implode( '; ', $cookie );


    if ( strtolower($_SERVER['REQUEST_METHOD']) == 'post' ) {
        curl_setopt( $ch, CURLOPT_POST, true );
        curl_setopt( $ch, CURLOPT_POSTFIELDS, file_get_contents('php://input') );
    }
    if ( strtolower($_SERVER['REQUEST_METHOD']) == 'put' ) {
        curl_setopt( $ch, CURLOPT_POST, true );
        curl_setopt( $ch, CURLOPT_POSTFIELDS, file_get_contents('php://input') );
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'PUT');
    }

    if ( strtolower($_SERVER['REQUEST_METHOD']) == 'delete' ) {
        curl_setopt( $ch, CURLOPT_POST, true );
        curl_setopt( $ch, CURLOPT_POSTFIELDS, file_get_contents('php://input') );
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'DELETE');
    }

    if(!empty($cookie)) {
        curl_setopt( $ch, CURLOPT_COOKIE, $cookie );
    }

    $headers = array();
    foreach ( getallheaders() as $key => $value ) {
        if ($key == 'Host' && $header_host) {
            $headers[] = $key . ': ' . $header_host;
        } else {
            $headers[] = $key . ': ' . $value;
        }
        
    }

    curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers );

    curl_setopt( $ch, CURLOPT_FOLLOWLOCATION, true );
    curl_setopt( $ch, CURLOPT_HEADER, true );
    curl_setopt( $ch, CURLOPT_SSL_VERIFYPEER, false );
    curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );

    curl_setopt( $ch, CURLOPT_USERAGENT, !empty($_GET['user_agent']) ? $_GET['user_agent'] : $_SERVER['HTTP_USER_AGENT'] );

    curl_setopt( $ch, CURLOPT_TIMEOUT, 240 );


    $result = curl_exec($ch);
    $curl_info = curl_getinfo($ch);

    if ( $result ) {
        $header_size = $curl_info["header_size"];
        $headers = substr($result, 0, $header_size);
        $contents = substr($result, $header_size);

        $header_text = preg_split( '/[\r\n]+/', $headers );
        foreach ( $header_text as $header ) {
            if (strlen($header) == 0) {
                continue;
            }
            // Необъяснимая 500 ошибка на продакшне, при попытке выставить Transfer-Encoding
            if (!preg_match('/^(?:Transfer-Encoding):/i', $header)) {
                header($header);
            }

            //				if ( preg_match( '/^(?:Content-Type|Content-Encoding|Content-Language|Set-Cookie):/i', $header ) ) {
            //					header( $header );
            //				}
        }

        echo $contents;
    }else {
        echo "Сервер недоступен";
    }


    curl_close( $ch );
}

//proxy_url("http://test.localhost".$_SERVER["REQUEST_URI"]);
if ( preg_match("/^\\/api\\/v1\\//", $_SERVER["REQUEST_URI"]) ) { 
        proxy_url("http://127.0.0.1:8080/tmis-ws-medipad/rest/tms-registry/".preg_replace("/^\\/api\\/v1\\//", "", $_SERVER["REQUEST_URI"]));
}else if( preg_match("/^\\/data\\/(auth|roles|changeRole)\\//", $_SERVER["REQUEST_URI"]) ) {
    proxy_url("http://".$core_host."/tmis-ws-medipad/rest/tms-auth/".preg_replace("/^\\/data\\//", "", $_SERVER["REQUEST_URI"]));
}else if( preg_match("/^\\/data\\/(print-by-context)\\//", $_SERVER["REQUEST_URI"]) ){
    proxy_url("http://".$print_system_host."/print_subsystem/print_template", $print_system_host);    
}else {
    proxy_url("http://".$core_host."/tmis-ws-medipad/rest/tms-registry/".preg_replace("/^\\/data\\//", "", $_SERVER["REQUEST_URI"]));
}

?>
