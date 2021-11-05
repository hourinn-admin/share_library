package cn.horinn.sharelib

@groovy.transform.Field
def	GITHUB_URL='https://github.com'


@groovy.transform.Field
def GITHUB_ORG='hourinn-admin'

@groovy.transform.Field
def GITHUB_CREDENTIALS_ID='GIT_PRE_TOKEN'


/**
 * 
 * 指定したブランチのソースをカレントディレクトリにチェックアウトする
 * @param para(gitUrl,owner,repo,branch,credentialsId,pathList)
*/
def checkoutForBranch(para) {
	def gitUrl = para.gitUrl
	def owner = para.owner
	def repo = para.repo
	def credentialsId = para.credentialsId 
	def pathList = para.pathList

	if (gitUrl == null) {
		gitUrl = GITHUB_URL
	}

	if (owner == null) {
		owner = GITHUB_ORG
	}

	if (credentialsId == null) {
		credentialsId = GITHUB_CREDENTIALS_ID
	}

	if (pathList == null) {
		pathList=["/*"]
	}

	def filesAsPaths = pathList.collect {
		[path:it]
	}

	checkout([$class:'GitSCM',
                branches:[[name:para.branch]],
                extensions:[
            		[$class:'CloneOption',
                        noTags:true,
                        shallow:true],
                    [$class:'SparseCheckoutPaths',
                        sparseCheckoutPaths:filesAsPaths]
                    ]
                ],
                userRemoteConfigs: [[
                    credentialsId: credentialsId,
                    url: "${gitUrl}/${owner}/${para.repo}.git",
                    refspec:"+refs/heads/${para.branch}:refs/remotes/origin/${para.branch}"
            ]]
    ])
}