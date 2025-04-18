package controller

type GuessNumberAPI struct {
}

type GuessRequest struct {
}

type GuessResponse struct {
}

func NewGuessNumberAPI() *GuessNumberAPI {
	return &GuessNumberAPI{}
}

func (api GuessNumberAPI) bet(request GuessRequest) *GuessResponse {
	//訂單生成

	//寫入訂單

	//取得錢包

	//扣款
	//扣款成功

	//更新訂單

	//等待開獎

	//結算訂單

	//派發獎勵

	//扣款失敗

	return &GuessResponse{}
}
