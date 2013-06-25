<?php
$response = array();
if(!isset($_GET['type'])) {
	$response['msg'] = "No action provided!... Dying...";
	$response['code'] = 1;
	echo json_encode($response);
	die();
}
if(!isset($_GET['input'])) {
	$response['msg'] = "No input provided!... Dying...";
	$response['code'] = 2;
	echo json_encode($response);
	die();
}
$response['msg'] = "Successful";
$response['code'] = 0;
switch ($_GET['type']) {
	case "len":
		$response['data'] = strlen($_GET['input']);
		break;
	case "echo":
		$response['data'] = $_GET['input'];
		break;
	default:
		$response['msg'] = "Invalid action type... Dying...";
		$response['code'] = 3;
		break;
}
echo json_encode($response);
?>