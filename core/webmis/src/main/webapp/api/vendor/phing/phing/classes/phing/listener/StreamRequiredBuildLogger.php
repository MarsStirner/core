<?php
/*
 *  $Id: c3668c408708d45a0a6b75bf2d750f71ecc7f7cd $
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * This software consists of voluntary contributions made by many individuals
 * and is licensed under the LGPL. For more information please see
 * <http://phing.info>.
 */

require_once 'phing/BuildLogger.php';

/**
 * Interface for build loggers that require that out/err streams be set in order to function.
 * 
 * This is just an empty sub-interface to BuildLogger, but is used by Phing to throw
 * graceful errors when classes like phing.listener.DefaultLogger are being used as 
 * -listener.
 *
 * @author    Hans Lellelid <hans@xmpl.org>
 * @version   $Id: c3668c408708d45a0a6b75bf2d750f71ecc7f7cd $
 * @see       BuildEvent
 * @see       Project::addBuildListener()
 * @package   phing
 */
interface StreamRequiredBuildLogger extends BuildLogger {

}