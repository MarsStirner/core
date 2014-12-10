#!/usr/bin/env php
<?php

//Bootstrap our Silex application
$app = require __DIR__ . '/../app/bootstrap.php';

//Include the namespaces of the components we plan to use
use Symfony\Component\Console\Application;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Input\InputArgument;
use Symfony\Component\Console\Input\InputOption;
use Symfony\Component\Console\Output\OutputInterface;


use Webmis\Models\ActionQuery;

//Instantiate our Console application
$console = new Application('WEBMIS Console', '0.1');

$console->register( 'close-appeals-docs' )
  ->setDescription('Закрыть незакрытые документы закрытых историй болезней')
  ->setHelp('Использование: <info>./console.php close-appeals-docs</info>')
  ->setCode(
    function(InputInterface $input, OutputInterface $output) use ($app)
    {
        $output->write( "Закрываем ...\n");

        //дата закрытия
        $closeDate = new \DateTime('NOW');
        $checkDate = new \DateTime('NOW');

        //текущая дата - дата изменения документов
        $modifyDatetime = new \DateTime('NOW');

        $days = 3;//три рабочих дня...

        //+++++++++++учёт выходных++++++++++++++++++++++
        $t = $checkDate->getTimestamp();

        for($i=0; $i < $days; $i++){

            // один день..
            $day = 86400;

            // каким днём недели является следующий день
            $nextDay = date('w', ($t-$day));

            // если суббота или воскресенье
            if($nextDay == 0 || $nextDay == 6) {
                $t = $t-$day;// отнимаем день
            }

            // отнимаем день
            $t = $t-$day;
        }

        $checkDate->setTimestamp($t);
        //++++++++++++++++++++++++++++++++++++++++++++++++

        $date = $checkDate->format('Y-m-d');
        $output->write("проверяем документы для иб закрытых раньше ".$date."\n");


        $sql = "SELECT a.endDate, at.* FROM Event as e join Action as a on a.event_id = e.id join ActionType as at on at.id = a.actionType_id where e.execDate < :checkDate and a.endDate is null and at.mnem in ('EXAM','EPI','JOUR','ORD','NOT','OTH')";
        $stmt = $app['db']->prepare($sql);
        $stmt->bindValue('checkDate', $checkDate, "datetime");
        $stmt->execute();
        $actions = $stmt->fetchAll();
        $count = count($actions);

        $output->write( "незакрытых документов: ".$count."  \n");

        if($count == 0){
            return;
        }

        $output->write( "закрываем  \n");
        $update_sql = "UPDATE Action as a"
                ." JOIN ActionType as at ON a.actionType_id = at.id "
                ." JOIN Event as e ON a.event_id = e.id "
                ." SET a.endDate = :closeDate, a.status = 2, a.modifyDatetime = :modifyDatetime "
                ." WHERE e.execDate < :checkDate "
                ." AND a.endDate IS NULL";

        $statment = $app['db']->prepare($update_sql);
        $statment->bindValue('modifyDatetime', $modifyDatetime, "datetime");
        $statment->bindValue('closeDate', $closeDate, "datetime");
        $statment->bindValue('checkDate', $checkDate, "datetime");
        $count = $statment->execute();

        $output->write( "закрыли \n");


    }
  );

$console->run();
